package com.artgeektech.househub.service;

import com.artgeektech.househub.common.Constant;
import com.artgeektech.househub.common.PageData;
import com.artgeektech.househub.common.PageParams;
import com.artgeektech.househub.domain.House;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendService {

    private static final String HOT_HOUSE_KEY = "hot_house";

    private static final Logger logger = LoggerFactory.getLogger(RecommendService.class);

    @Autowired
    private HouseService houseService;

    public void increase(Long id) {
        try {
          Jedis jedis = new Jedis("127.0.0.1");
          jedis.zincrby(HOT_HOUSE_KEY, 1.0D, id + "");
          jedis.zremrangeByRank(HOT_HOUSE_KEY, 0, -11);// 0代表第一个元素,-1代表最后一个元素，保留热度由低到高末尾10个房产
          jedis.close();
        } catch (Exception e) {
          logger.error(e.getMessage(),e);
        }

    }

    private List<Long> getMostViewedHouseIds() {
        try {
          Jedis jedis = new Jedis("127.0.0.1");
          Set<String> idSet = jedis.zrevrange(HOT_HOUSE_KEY, 0, -1);
          jedis.close();
          List<Long> ids = idSet.stream().map(Long::parseLong).collect(Collectors.toList());
          logger.info("ids size " + ids.size());
          return ids;
        } catch (Exception e) {
          logger.error(e.getMessage(), e);//有同学反应在未安装redis时会报500,在这里做下兼容,
          return Lists.newArrayList();
        }
    }

    public List<House> getMostViewedHouses() {
        List<Long> ids = getMostViewedHouseIds();
        List<House> houses = new ArrayList<>();
        if (ids == null || ids.size() == 0) {
            return houses;
        }
        ids = ids.subList(0, Math.min(ids.size(), Constant.RECOM_MOST_VIEWED_HOUSE_SIZE));
        for (Long id: ids) {
            houses.add(houseService.find(id));
        }
        logger.info("inside  getMostViewedHouses");
        logger.info(houses.get(0).getFirstImg());
        return houses;
    }


    public List<House> getLatestHouses() {
        House query = new House();
        PageData<House> pageData = houseService.queryPageHouse(
            query, PageParams.build(Constant.RECOM_LATEST_HOUSE_SIZE, 1)
        );
        logger.info("latest size " + pageData.getList().size());
        return pageData.getList();
    }
}
