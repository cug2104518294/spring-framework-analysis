package org.springframework.context;

import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.lang.Nullable;

/**
 * Central interface to provide configuration for an application. This is read-only while the
 * application is running, but may be reloaded if the implementation supports this.
 *
 * <p>An ApplicationContext provides:
 * <ul>
 * <li>Bean factory methods for accessing application components.
 * Inherited from {@link org.springframework.beans.factory.ListableBeanFactory}.
 * <li>The ability to load file resources in a generic fashion.
 * Inherited from the {@link org.springframework.core.io.ResourceLoader} interface.
 * <li>The ability to publish events to registered listeners.
 * Inherited from the {@link ApplicationEventPublisher} interface.
 * <li>The ability to resolve messages, supporting internationalization.
 * Inherited from the {@link MessageSource} interface.
 * <li>Inheritance from a parent context. Definitions in a descendant context
 * will always take priority. This means, for example, that a single parent
 * context can be used by an entire web application, while each servlet has
 * its own child context that is independent of that of any other servlet.
 * </ul>
 *
 * <p>In addition to standard {@link org.springframework.beans.factory.BeanFactory}
 * lifecycle capabilities, ApplicationContext implementations detect and invoke
 * {@link ApplicationContextAware} beans as well as {@link ResourceLoaderAware},
 * {@link ApplicationEventPublisherAware} and {@link MessageSourceAware} beans.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see ConfigurableApplicationContext
 * @see org.springframework.beans.factory.BeanFactory
 * @see org.springframework.core.io.ResourceLoader
 * <p>
 * <p>
 * BeanFactory:spring管理bean的顶级接口,俗称基本容器,在该容器下有两个子接口分别是HierarchicalBeanFactory和ListableBeanFactory.
 * HierarchicalBeanFactory:是一个具有层级关系的BeanFactory,其中包含一个很重要的属性为parentBeanFactory.
 * ListableBeanFactory:是以枚举的方式列举当前beanFactory中所有的bean对象.
 * ApplicationEventPublisher:是一个封装事件发布的接口.
 * ResourceLoader:是spring用来加载资源的顶级接口,主要是从给定的资源中加载文件.
 * MessageSource:是一个解析message的顶级策略接口,在平时的开发中我们几乎用不到.
 * EnvironmentCapable:用于获取当前容器的上下文环境.
 */

public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory,
		HierarchicalBeanFactory,
		MessageSource, ApplicationEventPublisher, ResourcePatternResolver {

	/**
	 * Return the unique id of this application context.
	 *
	 * @return the unique id of the context, or {@code null} if none
	 */
	@Nullable
	String getId();

	/**
	 * Return a name for the deployed application that this context belongs to.
	 *
	 * @return a name for the deployed application, or the empty String by default
	 */
	String getApplicationName();

	/**
	 * Return a friendly name for this context.
	 *
	 * @return a display name for this context (never {@code null})
	 */
	String getDisplayName();

	/**
	 * Return the timestamp when this context was first loaded.
	 *
	 * @return the timestamp (ms) when this context was first loaded
	 */
	long getStartupDate();

	/**
	 * Return the parent context, or {@code null} if there is no parent and this is the root of the
	 * context hierarchy.
	 *
	 * @return the parent context, or {@code null} if there is no parent
	 */
	@Nullable
	ApplicationContext getParent();

	/**
	 * Expose AutowireCapableBeanFactory functionality for this context.
	 * <p>This is not typically used by application code, except for the purpose of
	 * initializing bean instances that live outside of the application context, applying the Spring
	 * bean lifecycle (fully or partly) to them.
	 * <p>Alternatively, the internal BeanFactory exposed by the
	 * {@link ConfigurableApplicationContext} interface offers access to the {@link
	 * AutowireCapableBeanFactory} interface too. The present method mainly serves as a convenient,
	 * specific facility on the ApplicationContext interface.
	 * <p><b>NOTE: As of 4.2, this method will consistently throw IllegalStateException
	 * after the application context has been closed.</b> In current Spring Framework versions, only
	 * refreshable application contexts behave that way; as of 4.2, all application context
	 * implementations will be required to comply.
	 *
	 * @return the AutowireCapableBeanFactory for this context
	 * @throws IllegalStateException if the context does not support the {@link AutowireCapableBeanFactory}
	 *                               interface, or does not hold an autowire-capable bean factory
	 *                               yet (e.g. if {@code refresh()} has never been called), or if
	 *                               the context has been closed already
	 * @see ConfigurableApplicationContext#refresh()
	 * @see ConfigurableApplicationContext#getBeanFactory()
	 */
	AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException;

}
