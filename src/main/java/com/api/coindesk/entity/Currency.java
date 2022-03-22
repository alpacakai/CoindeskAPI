package com.api.coindesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CURRENCY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
	
	@Id
    @NotEmpty(message = "幣別英文為空")
	@Length(max = 10, message = "長度超過10")
	String en;
	
	@Column
    @NotEmpty(message = "幣別中文為空")
	@Length(max = 10, message = "長度超過10")
	String zh;
}
