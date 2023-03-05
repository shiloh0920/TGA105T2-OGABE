package com.ogabe.forum.controller;

import com.ogabe.forum.entity.ArticleEntity;
import com.ogabe.forum.entity.ArticleTypeEntity;
import com.ogabe.forum.entity.CollectionEntity;
import com.ogabe.forum.entity.ReplyEntity;

import com.ogabe.forum.Service.ArticleService;
import com.ogabe.forum.Service.ArticleTypeService;
import com.ogabe.forum.Service.CollectionService;
import com.ogabe.forum.Service.ReplyService;
import com.ogabe.user.entity.UserVO;
import com.ogabe.user.security.UserPrincipal;
import com.ogabe.user.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

import com.ogabe.forum.entity.ReplyEntity;

import jdk.jfr.Threshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ForumController {
	@Autowired
	ArticleService articleService;

	@Autowired
	ArticleTypeService articleTypeService;

	@Autowired
	ReplyService replyService;

	@Autowired
	UserService userService;

	@Autowired
	CollectionService collectionService;

	@GetMapping("/read")
	public String read(Model model) {

		return "forumRead";
	}

	@GetMapping("/read/{id}")
	public String Read(@PathVariable Integer id, Model model) {
		ArticleEntity articleEntity = articleService.findById(id);
		
		UserVO uservo = null;
		UserPrincipal principal1 = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		uservo = principal1.getUservo();
		// 重構 UserVO user=userService.find(2);
		ReplyEntity reply = new ReplyEntity();
		if (articleEntity == null) {
			articleEntity = new ArticleEntity();
		}

		List<ReplyEntity> list = replyService.find(articleEntity.getArticleid());
		reply.setArticleEntity(articleEntity);
		UserVO user = reply.getUserVo();
//        List<ReplyEntity> list= articleEntity.getReplyEnitySet();

//        UserVO  user=articleEntity.getUserVo();
		// 重構 articleEntity.setUserVo(user);
		// 重構 User user1=articleEntity.getUser();

		model.addAttribute("articleEntity", articleEntity);
		model.addAttribute("lister", list);
        model.addAttribute("user",uservo);

		return "forumRead";
	}

	@RequestMapping("/forumPost")
	public String create(Model model) {
		model.addAttribute("articleEntity", new ArticleEntity());
		List<ArticleTypeEntity> articleEntityList = articleTypeService.findAll();
		model.addAttribute("articleall", articleEntityList);
		return "forumPost";
	}

	/*
	 * 加入Valid驗證 錯誤訊息交給Spring容器處理，因為與其他參數擺放執行順序有問題，暫時先拿掉
	 */
	@PostMapping("/create")
	public String create(final RedirectAttributes redirectAttributes, 
						@RequestParam String articletitle,
						@RequestParam String articlecontext, 
						@RequestParam(name = "articletypeid") Integer typeid, 
						@RequestParam(name="file")MultipartFile file,
						Model model
						) throws IOException {
		
		Map<String,String> map=new HashMap<>();
		if ("".equals(articletitle) || articletitle.trim().length()==0) {
			redirectAttributes.addFlashAttribute("errmsgsa", "以下欄位不能為空");
			map.put("errmsgs","文章標題不能為空,請重新輸入");
			return "redirect:/forumPost";
		}
		if ("".equals(articlecontext) || articlecontext.trim().length()==0) {
			redirectAttributes.addFlashAttribute("errmsgsb", "以下欄位不能為空");
			map.put("errmsgs","文章內容不能為空,請重新輸入");
			return "redirect:/forumPost";
		}
		if(!map.isEmpty()) {
			
			redirectAttributes.addFlashAttribute("error",map);
			return "redirect://forumPost";
		}
		ArticleEntity articleEntity = new ArticleEntity();
		UserVO uservo = null;
		UserPrincipal principal1 = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		uservo = principal1.getUservo();
		articleEntity.setArticlecontext(articlecontext);
		articleEntity.setArticletitle(articletitle);
		ArticleTypeEntity articleTypeEntity = articleTypeService.find(typeid);
		articleEntity.setArticleTypeEntity(articleTypeEntity);
		articleEntity.setUserVo(uservo);
		if(file.getOriginalFilename()!="") {
			articleEntity.setArticleimg(file.getBytes());
		}
//		System.out.println("------" + uservo.getUserid() + "-------");
		
		ArticleEntity articleEntity1 = articleService.add(articleEntity);
		if (articleEntity1 != null) {
			redirectAttributes.addFlashAttribute("success", "<" + articleEntity1.getArticletitle() + ">" + "新增成功！");
			redirectAttributes.addFlashAttribute("ignore", "ignore");
		}

		return "redirect:/forum";
	}

	@PostMapping("/context")
	public List<ArticleEntity> findByContext(@RequestParam String context) {
		return articleService.findByS(context);
	}

	/*
	 * 註冊尋找文章頁面
	 */
	@GetMapping("/forumSearch")
	public String pageSearch(Model model) {

		return "forumSearch";
	}

	@PostMapping("/search")
	public String articleSearch(Model model, @RequestParam("article_search") String key) {

		if ("".equals(key)) {
			String errmsgs = "請輸入關鍵字";
			model.addAttribute("errmsgs", errmsgs);
			return "forumSearch";
		}
		List<ArticleEntity> articleEntities = articleService.findByWords(key, key);
		if (articleEntities.isEmpty()) {
			String errmsgs = "查無符合條件的資料";
			model.addAttribute("errmsgs", errmsgs);
			return "forumSearch";
		}
		int count = articleEntities.size();
		String total = "符合條件的資料有" + count + "筆資料";
		model.addAttribute("total", total);
		model.addAttribute("articleEntities", articleEntities);
		return "forumSearch";
	}

	/*
	 * 文章列表首頁 獲取全部文章資料 分頁查詢 SIze=5,返回JSON格式 ,目前寫死(2.26) 根據傳入參數決定頁數 page0才是第一頁
	 */
	@ResponseBody
	@GetMapping("/findbypages")
	public Page<ArticleEntity> findAByPages(@RequestParam(defaultValue = "1") int page,
											@RequestParam(defaultValue = "3") int size) {
		Sort sort = Sort.by(Sort.Direction.ASC, "articleid");
		return articleService.findAllByPage(PageRequest.of(page, size, sort));
	}

	@RequestMapping("/forum")
	public String index(@RequestParam(defaultValue = "0") int page,
						@RequestParam(defaultValue = "5") int size,
						Model model) {
		Sort sort = Sort.by(Sort.Direction.DESC, "postdatetime");
		Page<ArticleEntity> pages = articleService.findAllByPage(PageRequest.of(page, size, sort));
		// List<ArticleEntity> articleEntityList =articleService.findAll();
		model.addAttribute("page", pages);

		
		
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String username = null;
		UserVO uservo = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserPrincipal) {
			uservo = userService.getUserByEmail(((UserPrincipal) principal).getUsername());
		} 
		
		model.addAttribute("msg","ok");
		model.addAttribute("user", uservo);
		
		List<ArticleEntity> update=articleService.findAll();
//		model.addAttribute("user", uservo); 
		model.addAttribute("update",update);
			return "forum";
		
		
	}

	@ResponseBody
	@GetMapping("/jpql")
	public List<ArticleEntity> findByJPQL(@RequestParam int length) {
		return articleService.findByJPQL(length);
	}

	@ResponseBody
	@GetMapping("/update")
	public int findByJPql(@RequestParam String articletitle, 
						  @RequestParam String articlecontext,
			@RequestParam Integer articleid) {
		return articleService.findByJPQL(articletitle, articlecontext, articleid);
	}

	@GetMapping("/index")
	public String index() {

		return "index";
	}

	@ResponseBody
	@GetMapping("/addtype")
	public void add() {
		ArticleEntity articleEntity = new ArticleEntity();

		ArticleTypeEntity articleTypeEntity = articleTypeService.find(3);

		articleEntity.setArticleTypeEntity(articleTypeEntity);

		articleEntity.setArticletitle("springboot");
		articleEntity.setArticlecontext("fun");
//       articleEntity.setUserid(1);

		articleService.add(articleEntity);

		String a = articleEntity.getArticleTypeEntity().getArticletypedetail();
		System.out.println(a);
	}

	@GetMapping("/ok")
	public ArticleEntity findReply() {

		ArticleEntity articleEntity = articleService.findById(2);

		return articleEntity;

	}

	@GetMapping("/pp")
	public String ok() {

		return "pra";
	}

	@GetMapping("/comments")
	public String add(@RequestParam("replycontext") String context, 
					  @RequestParam("articleid") Integer id,
			final RedirectAttributes ra) {

		ArticleEntity articleEntity = articleService.findById(id);
		ReplyEntity replyEnity = new ReplyEntity();
		replyEnity.setReplycontext(context);
		replyEnity.setArticleEntity(articleEntity);
		UserVO uservo = null;
		UserPrincipal principal1 = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		uservo = principal1.getUservo();
		replyEnity.setUserVo(uservo);

		ReplyEntity r = replyService.add(replyEnity);

		if (r != null) {
			ra.addFlashAttribute("commentSuccess", "您對<" + r.getArticleEntity().getArticletitle() + ">本文章的留言新增成功！");
		}

		return "redirect:/forum";

//       測試聯表
//        String a=replyEnity.getArticleEntity().getArticlecontext();
//        System.out.println(a);
	}

	@GetMapping("/forumCollection")
	public String collect(Model model) {
		UserVO uservo = null;
		UserPrincipal principal1 = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		uservo = principal1.getUservo();
		CollectionEntity c = new CollectionEntity();
		Integer id = uservo.getUserid();
		List<CollectionEntity> list = collectionService.find(id);

//		List<CollectionEntity> list=collectionService.find(uservo);

		model.addAttribute("list", list);
		return "forumCollection";
	}

	@GetMapping("/addCollect")
	public String addCollect(@RequestParam("articleid") Integer id) {

		ArticleEntity ae = articleService.findById(id);
		CollectionEntity c = new CollectionEntity();
		c.setArticleEntity(ae);
		UserVO uservo = null;
		UserPrincipal principal1 = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		uservo = principal1.getUservo();
		c.setUservo(uservo);
		collectionService.add(c);

//    	System.out.println("-------"+id+"--------");
		return "redirect:/forumCollection";
	}	
		@GetMapping("/cancel/{cid}")
		public String cancel(@PathVariable("cid") Integer id) throws SQLException{
			
		collectionService.cancel(id);
		
		return"redirect:/forumCollection";
		}
	
		@GetMapping("/image/displays/{articleid}")
		@ResponseBody
		public void showImg(@PathVariable Integer articleid, HttpServletResponse res, ArticleEntity articleEntity)
		throws ServletException, IOException {
		System.out.println("---------------"+articleid);
		ArticleEntity a = articleService.findById(articleid);
		res.setContentType("image/jped, image/jpg, image/png, image/gif");
		res.getOutputStream().write(a.getArticleimg());
		res.getOutputStream().close();

	}

		@GetMapping("/welcome") 
		public String welcome() {
		return "weicome";
	}
//		@ResponseBody
		@GetMapping("/delete/{id}")
		public String deleteReply(@PathVariable("id") Integer replyid,
								  @RequestParam("aid")Integer articleid,
								final RedirectAttributes redirectAttributes) {
			ReplyEntity r=replyService.findOne(replyid);
			replyService.deleteReply(replyid);
			System.out.println(articleid);
			redirectAttributes.addFlashAttribute("deleteReply","提醒您,您以成功刪除 <"+r.getReplycontext()+">這則留言");
			
			return "redirect:/read/"+articleid;

				
		}
		@GetMapping("forumMine")
		public String my(Model model) {
			UserVO uservo = null;
			UserPrincipal principal1 = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			uservo = principal1.getUservo();
			Integer u=uservo.getUserid();
			List<ArticleEntity> list=articleService.findUserArticle(u);
//			UserVO user=userService.getUserById(u);
//			List<ArticleEntity> a=uservo.getArticleEntities();
			model.addAttribute("list",list);
			return "forumUserArticle";
		}

}
