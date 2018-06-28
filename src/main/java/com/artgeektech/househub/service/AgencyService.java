package com.artgeektech.househub.service;

import com.artgeektech.househub.common.Constant;
import com.artgeektech.househub.common.PageData;
import com.artgeektech.househub.common.PageParams;
import com.artgeektech.househub.domain.Agency;
import com.artgeektech.househub.domain.User;
import com.artgeektech.househub.repository.AgencyRepository;
import com.artgeektech.househub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by guang on 6:17 PM 5/2/18.
 */
@Service
public class AgencyService {

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public List<Agency> getAllAgency() {
        return agencyRepository.findAll();
    }

    public Agency save(Agency agency) {
        return agencyRepository.save(agency);
    }

    public Agency findOne(Long id) {
        return agencyRepository.findOne(id);
    }

    public PageData<User> getAllAgent(PageParams pageParams) {
        List<User> agents = userRepository.queryPageUser(
            Constant.AGENT_USER, pageParams.getOffset(), pageParams.getLimit()
        );
        agents.forEach(a -> {
            userService.setFullAvatarPath(a);
            a.setAgencyName(agencyRepository.findOne(a.getAgencyId()).getName());
        });
        Long totalCount = userRepository.queryPageCount(Constant.AGENT_USER);
        return PageData.buildPage(agents, totalCount, pageParams.getPageSize(), pageParams.getPageNum());
    }
}
