package com.ceshi.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/****
 * @Author:laowang
 * @Description:Other构建
 * @Date 2019/6/14 19:13
 *****/
@AllArgsConstructor
@Data
@NoArgsConstructor
@ApiModel(description = "Other",value = "Other")
@Table(name="other")
public class Other implements Serializable{

	@ApiModelProperty(value = "",required = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//

	@ApiModelProperty(value = "",required = false)
	@Column(name = "username")
	private String username;//

	@ApiModelProperty(value = "其他",required = false)
	@Column(name = "title")
	private String title;//其他




}
