package com.ceshi.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:laowang
 * @Description:User构建
 * @Date 2019/6/14 19:13
 *****/

@Data
@NoArgsConstructor
@ApiModel(description = "User",value = "User")
@Table(name="tb_user")
public class User implements Serializable{

	@ApiModelProperty(value = "",required = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;//

	@ApiModelProperty(value = "用户名",required = false)
	@Column(name = "username")
	private String username;//用户名

	@ApiModelProperty(value = "密码",required = false)
	@Column(name = "password")
	private String password;//密码

	@ApiModelProperty(value = "创建时间",required = false)
	@Column(name = "created")
	private Date created;//创建时间

	@ApiModelProperty(value = "金额",required = false)
	@Column(name = "money")
	private Double money;//金额


}
