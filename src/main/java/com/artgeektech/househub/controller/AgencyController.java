package com.artgeektech.househub.controller;

import com.artgeektech.househub.common.*;
import com.artgeektech.househub.domain.*;
import com.artgeektech.househub.service.*;
import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by guang on 5:37 PM 5/2/18.
 */
@Controller
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private UserService userService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private MailService mailService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RecommendService recommendService;

    @RequestMapping("agency/create")
    public String agencyCreate(){
        ResultMsg resultMsg = checkAdminSignin();
        if (resultMsg.isSuccess()) {
            return "/user/agency/create";
        } else {
            return "redirect:/accounts/signin?" + resultMsg.asUrlParams();
        }
    }

    @RequestMapping("agency/submit")
    public String agencySubmit(Agency agency) {
        ResultMsg resultMsg = checkAdminSignin();
        if (resultMsg.isSuccess()) {
            agencyService.save(agency);
            return "redirect:/index?" + resultMsg.asUrlParams();
        } else {
            return "redirect:/accounts/signin?" + resultMsg.asUrlParams();
        }
    }

    @RequestMapping("agency/list")
    public String agencyList(ModelMap modelMap) {
        List<Agency> agencies = agencyService.getAllAgency();
        List<House> mostViewedHouses = recommendService.getMostViewedHouses();
        List<House> latestHouses = recommendService.getLatestHouses();
        modelMap.put("mostViewedHouses", mostViewedHouses);
        modelMap.put("latestHouses", latestHouses);
        modelMap.put("agencyList", agencies);
        return "/user/agency/agencyList";
    }

    @RequestMapping("/agency/agencyDetail")
    public String agencyDetail(Long id, ModelMap modelMap) {
        Agency agency =  agencyService.findOne(id);
        List<House> mostViewedHouses = recommendService.getMostViewedHouses();
        List<House> latestHouses = recommendService.getLatestHouses();
        modelMap.put("mostViewedHouses", mostViewedHouses);
        modelMap.put("latestHouses", latestHouses);
        modelMap.put("agency", agency);
        return "/user/agency/agencyDetail";
    }

    private ResultMsg checkAdminSignin() {
        User user =  UserContext.getUser();
        if (user == null || !Objects.equal(user.getEmail(), "marcoshown26@gmail.com")) {
            return ResultMsg.error("admin signin required");
        }
        return ResultMsg.success();
    }

    @RequestMapping("/agency/agentList")
    public String agentList(Integer pageSize, Integer pageNum, ModelMap modelMap) {
        if (pageSize == null) {
            pageSize = Constant.AGENT_DEFAULT_PAGE_SIZE;
        }
        PageData<User> pageStatus = agencyService.getAllAgent(PageParams.build(pageSize, pageNum));
        List<House> mostViewedHouses = recommendService.getMostViewedHouses();
        List<House> latestHouses = recommendService.getLatestHouses();
        modelMap.put("mostViewedHouses", mostViewedHouses);
        modelMap.put("latestHouses", latestHouses);
        modelMap.put("ps", pageStatus);
        return "/user/agent/agentList";
    }

    @RequestMapping("/agency/agentDetail")
    public String agentDetail(Long id, ModelMap modelMap) {
        User agent = userService.findOne(id);
        agent.setAgencyName(agencyService.findOne(agent.getAgencyId()).getName());
        PageData<House> bindHouse = houseService.queryPageHouseUser(
            id, HouseUserType.SALE.value, PageParams.build(4, 1)
        );
        List<Comment> commentList = commentService.getAgentComments(id, Constant.COMMENT_SIZE_LIMIT);
        modelMap.put("bindHouses", bindHouse.getList());
        List<House> mostViewedHouses = recommendService.getMostViewedHouses();
        List<House> latestHouses = recommendService.getLatestHouses();
        modelMap.put("mostViewedHouses", mostViewedHouses);
        modelMap.put("latestHouses", latestHouses);
        modelMap.put("agent", agent);
        modelMap.put("commentList", commentList);
        return "/user/agent/agentDetail";
    }

    @PostMapping("/agency/agentMsg")
    public String agentMsg(UserMsg userMsg) {
        Long agentId = userMsg.getAgentId();
        User agent = userService.findOne(agentId);
        String text = String.format("Hi %s, this is %s inquiring buying a house. %s. Contact me via %s",
            agent.getName(), userMsg.getUserName(), userMsg.getMsg(), userMsg.getUserEmail()
        );
        mailService.sendMail("Inquiry buying house", agent.getEmail(), text);
        return "redirect:/agency/agentDetail?id=" + agentId + "&" +
            ResultMsg.success("message to agent sent").asUrlParams();
    }

}
