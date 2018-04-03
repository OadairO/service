package com.supermap.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermap.log.entity.UserOperationLog;
import com.supermap.log.service.LogService;
import com.supermap.too.TableVoo;

@RestController
public class LogController {
	
	@Autowired
	LogService logService;

	@RequestMapping(value = "/getLogList")
	public TableVoo getLogList(TableVoo table,UserOperationLog log) {
		return logService.getLogList(table, log);
	}
}
