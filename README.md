flume-ng-mongodb-sink
=============

### 主要功能介绍
	利用flume将nginx的日志记录到mongodb。并且将数据格式化json数据，方便制作报表和统计。
===待完成===
	后续会增加针对tomcat日，linux系统日志的分析。
	

### 针对nginx的日志，修改MongoSink。
nginx的日志格式配置代码
	http {
	...
	userid on;
	log_format main '$remote_addr^$time_local^$request^$status^$body_bytes_sent^$http_referer^$http_user_agent^$uid_got  $http_cookie';
	access_log  logs/access.log  main;
	...
	}
	
filename：nginx.access.flume
	
	agent1.sources = ngrinder
	agent1.sources.ngrinder.type = exec 
	agent1.sources.ngrinder.command = tail -F /home/nginx/logs/access.log
	agent1.sources.ngrinder.channels = mc1
	agent1.channels = mc1
	agent1.channels.mc1.type = memory
	agent1.channels.mc1.capacity = 100
	agent1.sinks = sink2
	agent1.sinks.sink2.type = org.riderzen.flume.sink.MongoSink
	agent1.sinks.sink2.host = you.mongodb.ip
	agent1.sinks.sink2.port = 27017
	agent1.sinks.sink2.model = single
	agent1.sinks.sink2.autoWrap=true
	agent1.sinks.sink2.db=qegoo
	agent1.sinks.sink2.collection = nginxlog
	search.sinks.sink2.search=search
	agent1.sinks.sink2.batch = 100
	agent1.sinks.sink2.channel = mc1    
	
### 配置说明  
	search.sinks.sink2.search    =search
	在请求的url中进行查询，如果存在 “search”则记录日志。否者放弃。
	
	
	

### 运行flume


命令 ：/home/flume/apache-flume-1.4.0-bin/bin/flume-ng agent -c conf -f /home/flume/conf/nginx.access.flume  -n agent1





