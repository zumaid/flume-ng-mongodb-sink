flume-ng-mongodb-sink
=============
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
	agent1.sinks.avro-sink.type = avro
	agent1.sinks.avro-sink.channel = mc1
	agent1.sinks.avro-sink.hostname = 192.168.9.33
	agent1.sinks.avro-sink.port = 4545
	agent1.sinks.sink2.type = org.riderzen.flume.sink.MongoSink
	agent1.sinks.sink2.host = you.mongodb.ip
	agent1.sinks.sink2.port = 27017
	agent1.sinks.sink2.model = single
	agent1.sinks.sink2.autoWrap=true
	agent1.sinks.sink2.db=qegoo
	agent1.sinks.sink2.collection = nginxlog
	agent1.sinks.sink2.batch = 100
	agent1.sinks.sink2.channel = mc1    

### 运行flume


命令 ：/home/flume/apache-flume-1.4.0-bin/bin/flume-ng agent -c conf -f /home/flume/conf/nginx.access.flume  -n agent1





