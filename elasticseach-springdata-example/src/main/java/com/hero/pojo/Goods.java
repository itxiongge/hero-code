package com.hero.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * indexName  设置索引库名称
 * type  设置类型的名称
 * shards  设置分片数，默认是5
 * replicas 设置副本数，默认是1
 */
@Data
@Document(indexName = "hero2",type = "goods",shards = 5,replicas = 1)
@Builder
public class Goods {

    public Goods() {
    }

    public Goods(Long id, String title, String category, String brand, Double price, String images) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.images = images;
    }

    /**
     * 绑定主键，将索引库中的类型里_id,主键，与当前属性进行绑定
     */
    @Id
    private Long id;//商品的唯一标识
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String title;//标题
    @Field(type = FieldType.Keyword)
    private String category;//分类
    @Field(type = FieldType.Keyword)
    private String brand;//品牌
    @Field(type = FieldType.Double)
    private Double price;//价格
    @Field(type = FieldType.Keyword,index = false)
    private String images;//图片地址
}
