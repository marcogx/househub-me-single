package com.artgeektech.househub.domain;

import com.artgeektech.househub.common.Constant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by guang on 2:54 PM 4/30/18.
 */
@Entity
@Table(name = "house")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdTime", "updatedTime"}, allowGetters = true)
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private List<Long> ids;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Integer type = 1; // 1 for sell, 2 for rent

    @NotNull
    private Integer price;

    @Column(nullable = false, length = 40)
    private String name = "";

    @Column(nullable = false, length = 1024)
    private String images;

    @NotNull
    private Integer area;

    @Column(nullable = false, columnDefinition = "TINYINT(3)")
    private Integer beds;

    @Column(nullable = false, columnDefinition = "TINYINT(3)")
    private Integer baths;

    @NotNull
    private Double rating;

    @Transient
    private Integer roundRating = 0;

    @NotNull
    private String remarks;

    @NotNull
    private String properties;

    @NotNull
    private String floorPlan;

    @NotNull
    private String tags;

    @NotNull
    private Integer zipcode;

    @NotNull
    private String address;

    @Transient
    private String firstImg;

    @Transient
    private List<String> imageList = Lists.newArrayList();

    @Transient
    private List<String> floorPlanList = Lists.newArrayList();

    @Transient
    private List<String> featureList   = Lists.newArrayList();

    @Transient
    private List<MultipartFile> houseFiles;

    @Transient
    private List<MultipartFile> floorPlanFiles;

    @Transient
    private String priceStr;

    @Transient
    private String typeStr;

    @Transient
    private Long userId;

    @Transient
    private boolean bookmarked;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Integer status;

    @Transient
    private String sort = "time_desc"; //price_desc,price_asc,time_desc

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createTime;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
        if (!Strings.isNullOrEmpty(images)) {
            List<String> list =  Splitter.on(",").splitToList(images);
            if (list.size() > 0) {
                this.firstImg = list.get(0);
                this.imageList = list;
            }
        }
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Integer getBaths() {
        return baths;
    }

    public void setBaths(Integer baths) {
        this.baths = baths;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
        this.roundRating = (int) Math.round(rating);
    }

    public Integer getRoundRating() {
        return roundRating;
    }

    public void setRoundRating(Integer roundRating) {
        this.roundRating = roundRating;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
        if (!Strings.isNullOrEmpty(properties)) {
            this.featureList = Splitter.on(",").splitToList(properties);
        }
    }

    public String getFloorPlan() {
        return floorPlan;
    }

    public void setFloorPlan(String floorPlan) {
        this.floorPlan = floorPlan;
        if (!Strings.isNullOrEmpty(floorPlan)) {
            this.floorPlanList = Splitter.on(",").splitToList(floorPlan);
        }
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstImg() {
        if (firstImg == null) {
            if (!Strings.isNullOrEmpty(images)) {
                List<String> list =  Splitter.on(",").splitToList(images);
                if (list.size() > 0) {
                    firstImg = list.get(0);
                }
            }
        }
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public List<String> getImageList() {
        if (imageList == null || imageList.size() == 0) {
            if (!Strings.isNullOrEmpty(images)) {
                List<String> list =  Splitter.on(",").splitToList(images);
                if (list.size() > 0) {
                    this.firstImg = list.get(0);
                    this.imageList = list;
                }
            }
        }
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<String> getFloorPlanList() {
        return floorPlanList;
    }

    public void setFloorPlanList(List<String> floorPlanList) {
        this.floorPlanList = floorPlanList;
    }

    public List<String> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(List<String> featureList) {
        this.featureList = featureList;
        this.properties = Joiner.on(",").join(featureList);
    }

    public List<MultipartFile> getHouseFiles() {
        return houseFiles;
    }

    public void setHouseFiles(List<MultipartFile> houseFiles) {
        this.houseFiles = houseFiles;
    }

    public List<MultipartFile> getFloorPlanFiles() {
        return floorPlanFiles;
    }

    public void setFloorPlanFiles(List<MultipartFile> floorPlanFiles) {
        this.floorPlanFiles = floorPlanFiles;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public String getTypeStr() {
        if (typeStr == null) {
            if (this.type.equals(Constant.FOR_SELL)) {
                this.typeStr = "For Sale";
            } else if (this.type.equals(Constant.FOR_RENT)) {
                this.typeStr = "For Rent";
            }
        }
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "House{" +
            "id=" + id +
            ", ids=" + ids +
            ", type=" + type +
            ", price=" + price +
            ", name='" + name + '\'' +
            ", images='" + images + '\'' +
            ", area=" + area +
            ", beds=" + beds +
            ", baths=" + baths +
            ", rating=" + rating +
            ", roundRating=" + roundRating +
            ", remarks='" + remarks + '\'' +
            ", properties='" + properties + '\'' +
            ", floorPlan='" + floorPlan + '\'' +
            ", tags='" + tags + '\'' +
            ", zipcode=" + zipcode +
            ", address='" + address + '\'' +
            ", firstImg='" + firstImg + '\'' +
            ", imageList=" + imageList +
            ", floorPlanList=" + floorPlanList +
            ", featureList=" + featureList +
            ", houseFiles=" + houseFiles +
            ", floorPlanFiles=" + floorPlanFiles +
            ", priceStr='" + priceStr + '\'' +
            ", typeStr='" + typeStr + '\'' +
            ", userId=" + userId +
            ", bookmarked=" + bookmarked +
            ", status=" + status +
            ", sort='" + sort + '\'' +
            ", createTime=" + createTime +
            ", updatedTime=" + updatedTime +
            '}';
    }
}
