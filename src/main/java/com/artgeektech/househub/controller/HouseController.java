package com.artgeektech.househub.controller;

import com.artgeektech.househub.common.*;
import com.artgeektech.househub.domain.Comment;
import com.artgeektech.househub.domain.House;
import com.artgeektech.househub.domain.User;
import com.artgeektech.househub.service.*;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by guang on 2:51 PM 4/30/18.
 */
@Controller
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private UserService userService;

    @Autowired
    private RecommendService recommendService;

    @GetMapping({"/", "index"})
    public String index(ModelMap modelMap) {
        List<House> mostViewedHouses = recommendService.getMostViewedHouses();
        modelMap.put("mostViewedHouses", mostViewedHouses);
        return "homepage/index";
    }
    /**
     * 1.实现分页
     * 2.支持小区搜索、类型搜索
     * 3.支持排序
     * 4.支持展示图片、价格、标题、地址等信息
     * @return
     */
    @RequestMapping("/house/list")
    public String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap) {
        PageData<House> pageStatus = houseService.queryPageHouse(query,
            PageParams.build(pageSize, pageNum));
        modelMap.put("ps", pageStatus);
        modelMap.put("vo", query);
        List<House> mostViewedHouses = recommendService.getMostViewedHouses();
        List<House> latestHouses = recommendService.getLatestHouses();
        modelMap.put("mostViewedHouses", mostViewedHouses);
        modelMap.put("latestHouses", latestHouses);
        return "house/listing";
    }

    @RequestMapping("/house/toAdd")
    public String toAdd() {
        User user = UserContext.getUser();
        if (!user.getType().equals(Constant.AGENT_USER)) {
            return "redirect:/accounts/signin?" + ResultMsg.error("only agent can add house").asUrlParams();
        }
        return "/house/add";
    }

    @RequestMapping("/house/add")
    public String add(House house) {
        User user = UserContext.getUser();
        houseService.addHouse(house, user);
        return "redirect:/house/ownlist";
    }

    @RequestMapping("/house/ownlist")
    public String ownList(Integer pageNum, Integer pageSize, ModelMap modelMap, String pageType) {
        User user = UserContext.getUser();
        if (Strings.isNullOrEmpty(pageType)) {
            pageType = "own";
        }
        Integer type = pageType.equals("own") ? HouseUserType.SALE.value : HouseUserType.BOOKMARK.value;
        PageData<House> pageStatus = houseService.queryPageHouseUser(
            user.getId(), type, PageParams.build(pageSize, pageNum)
        );
        modelMap.put("ps", pageStatus);

        modelMap.put("pageType", pageType);
        return "/house/ownlist";
    }

    /**
     * 查询房屋详情
     * 查询关联经纪人
     * @param id
     * @return
     */
    @RequestMapping("house/detail")
    public String houseDetail(Long id, ModelMap modelMap) {
        House house = houseService.find(id);
        recommendService.increase(id);
        List<Comment> comments = commentService.getHouseComments(id, Constant.COMMENT_SIZE_LIMIT);
        User agent = userService.findAgentByHouse(id);
        List<House> mostViewedHouses = recommendService.getMostViewedHouses();
        List<House> latestHouses = recommendService.getLatestHouses();
        modelMap.put("mostViewedHouses", mostViewedHouses);
        modelMap.put("latestHouses", latestHouses);
        modelMap.put("agent", agent);
        modelMap.put("house", house);
        modelMap.put("commentList", comments);
        return "/house/detail";
    }

    //2.收藏
    @ResponseBody
    @RequestMapping("house/bookmark")
    public ResultMsg bookmark(Long id) {
        User user =	UserContext.getUser();
        houseService.bindUserToHouse(id, user.getId(), HouseUserType.BOOKMARK.value);
        return ResultMsg.success("Bookmark Success");
    }

    //3.删除收藏
    @ResponseBody
    @RequestMapping("house/unbookmark")
    public ResultMsg unbookmark(Long id) {
        User user =	UserContext.getUser();
        houseService.unbindUserToHouse(id, user.getId(), HouseUserType.BOOKMARK.value);
        return ResultMsg.success("Unbookmark Success");
    }

    @RequestMapping(value="house/del")
    public String deleteHouseUser(Long id, String pageType) {
        User user = UserContext.getUser();
        houseService.unbindUserToHouse(
            id, user.getId(), pageType.equals("own") ? HouseUserType.SALE.value : HouseUserType.BOOKMARK.value
        );
        return "redirect:/house/ownlist" + "?&pageType=" + pageType;
    }

    //4.收藏列表
    @RequestMapping("house/bookmarked")
    public String bookmarkList(Integer pageNum, Integer pageSize, ModelMap modelMap) {
        User user = UserContext.getUser();
        PageData<House> pageStatus = houseService.queryPageHouseUser(
            user.getId(), HouseUserType.BOOKMARK.value, PageParams.build(pageSize, pageNum)
        );
        modelMap.put("ps", pageStatus);
        modelMap.put("pageType", "book");
        return "/house/ownlist";
    }
}
