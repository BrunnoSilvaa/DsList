package com.Bsystem.Dscatalog.controllers.exception;

import java.io.Serializable;
import java.time.Instant;

import com.Bsystem.Dscatalog.entities.CategoryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandarError implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String path;
	private String error;
	private String message;
	private Integer status;
	private Instant timeStamp;

}
