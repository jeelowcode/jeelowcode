
/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，【允许商用】使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与【本软件竞争】的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.framework.utils.component.pool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class SyncPoolConfiguration {

	@Bean(name = "asyncPoolTaskExecutor")
	public ThreadPoolTaskExecutor executor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		//核心线程数
		taskExecutor.setCorePoolSize(5);
		//异步方法内部线程名称
		taskExecutor.setThreadNamePrefix("jeelowcode-async-");
		/**
		 * 当线程池的任务缓存队列已满并且线程池中的线程数目达到maximumPoolSize，如果还有任务到来就会采取任务拒绝策略
		 * 通常有以下四种策略：
		 * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
		 * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
		 * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
		 * ThreadPoolExecutor.CallerRunsPolicy：重试添加当前的任务，自动重复调用 execute() 方法，直到成功
		 */
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		taskExecutor.initialize();
		return taskExecutor;
	}

}
