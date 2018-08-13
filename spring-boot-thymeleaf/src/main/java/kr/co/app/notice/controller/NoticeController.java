package kr.co.app.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.app.notice.service.NoticeService;
import kr.co.app.notice.vo.NoticeVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("/list")
	public void list(Model model) {}
	
	@GetMapping("/listAjax")
	public @ResponseBody List<NoticeVO> listAjax() {
		return (List<NoticeVO>) noticeService.list();
	}
	
	@GetMapping("/read")
	public void read(@RequestParam(value="brdNo", required=true) int brdNo, Model model) {
		model.addAttribute("row", noticeService.read(brdNo));
	}
	
	@GetMapping("/form")
	public void form(@RequestParam(value="brdNo", required=false, defaultValue="0") int brdNo, Model model) {
		NoticeVO noticeVO = new NoticeVO();
		if (brdNo > 0) {
			noticeVO = noticeService.read(brdNo);
		}
		model.addAttribute("row", noticeVO);
	}
	
	@PostMapping("/save")
	public String save(@RequestParam(value="brdNo", required=false, defaultValue="0") int brdNo, NoticeVO paramVO) {
		// update
		if (brdNo > 0) {
			int cnt = noticeService.update(paramVO);
			log.info("--- update cnt : {}", cnt);
		}
		// insert
		else {
			int cnt = noticeService.insert(paramVO);
			log.info("--- insert cnt : {}", cnt);
		}
		return "redirect:/notice/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(value="brdNo", required=true) int brdNo) {
		int cnt = noticeService.delete(brdNo);
		log.info("--- delete cnt : {}", cnt);
		return "redirect:/notice/list";
	}
}
