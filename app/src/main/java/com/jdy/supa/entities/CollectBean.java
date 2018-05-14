package com.jdy.supa.entities;

import java.util.List;

/**
 * 收藏的酒店房间 后期需做改变
 */
public class CollectBean extends BaseBean{
    private String image;
    private double price;
    private String priceUnit; //价格单位
    private String character; //特征描述
    private String headImage;
    private String capacity; //具体容量描述
    private String title;
    private String content;
    private List<String> advantages; //优惠

    public CollectBean(String image, double price, String priceUnit, String character, String headImage,
                       String capacity, String title, String content, List<String> advantages) {
        this.image = image;
        this.price = price;
        this.priceUnit = priceUnit;
        this.character = character;
        this.headImage = headImage;
        this.capacity = capacity;
        this.title = title;
        this.content = content;
        this.advantages = advantages;
    }

    public String getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public String getCharacter() {
        return character;
    }

    public String getHeadImage() {
        return headImage;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getAdvantages() {
        return advantages;
    }
}
