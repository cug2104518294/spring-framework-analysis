package org.springframework.beans.factory.config;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.AttributeAccessor;
import org.springframework.lang.Nullable;

/**
 * Bean的定义信息（Bean的元信息） 包括Name、 Scope、 LazyInit、 Autowire等信息
 * <p>A BeanDefinition describes a bean instance, which has property values,
 * constructor argument values, and further information supplied by concrete implementations.
 *
 * <p>This is just a minimal interface: The main intention is to allow a
 * {@link BeanFactoryPostProcessor} such as {@link PropertyPlaceholderConfigurer} to introspect and
 * modify property values and other bean metadata.
 *
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @see ConfigurableListableBeanFactory#getBeanDefinition
 * @see org.springframework.beans.factory.support.RootBeanDefinition
 * @see org.springframework.beans.factory.support.ChildBeanDefinition
 * @since 19.03.2004
 */
public interface BeanDefinition extends AttributeAccessor, BeanMetadataElement {

	/**
	 * Scope identifier for the standard singleton scope: "singleton".
	 * <p>Note that extended bean factories might support further scopes.
	 *
	 * @see #setScope
	 */
	// 标准单例范围的范围标识符:“单例”。 单例
	String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

	/**
	 * Scope identifier for the standard prototype scope: "prototype".
	 * <p>Note that extended bean factories might support further scopes.
	 *
	 * @see #setScope
	 */
	// 标准原型范围标识符:“prototype”。  原型
	String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

	/**
	 ROLE_xxx 用来描述一个 Bean 的角色，
	 0 ROLE_APPLICATION表示这个 Bean 是用户自己定义的 Bean；
	 1 ROLE_SUPPORT 表示这个 Bean 是某些复杂配置的支撑部分；
	 2 ROLE_INFRASTRUCTURE 表示这是一个 Spring 内部的 Bean，
	 通过 setRole/getRole 可以修改
	 */
	/**
	 * 角色提示，指示BeanDefinition是应用程序的主要部分。通常对应于用户定义的bean。
	 * <p>Role hint indicating that a {@code BeanDefinition} is a major part
	 * of the application. Typically corresponds to a user-defined bean.
	 */
	int ROLE_APPLICATION = 0;

	/**
	 * Role hint indicating that a {@code BeanDefinition} is a supporting part of some larger
	 * configuration, typically an outer {@link org.springframework.beans.factory.parsing.ComponentDefinition}.
	 * {@code SUPPORT} beans are considered important enough to be aware of when looking more
	 * closely at a particular {@link org.springframework.beans.factory.parsing.ComponentDefinition},
	 * but not when looking at the overall configuration of an application.
	 */
	/*
		角色提示，指示BeanDefinition是某个较大配置(通常是外部配置)的支持部分
	 */
	int ROLE_SUPPORT = 1;

	/**
	 * Role hint indicating that a {@code BeanDefinition} is providing an entirely background role
	 * and has no relevance to the end-user. This hint is used when registering beans that are
	 * completely part of the internal workings of a {@link org.springframework.beans.factory.parsing.ComponentDefinition}.
	 */
	/*
		角色提示，指示BeanDefinition提供完全后台角色，与最终用户无关。
		当注册完全属于a内部工作的bean时，会使用这个提示
	 */
	int ROLE_INFRASTRUCTURE = 2;

	// Modifiable attributes

	/*
	 * setParentName/getParentName 用来配置 parent 的名称，
	 * 这个对应着 XML 中的 <bean parent=""> 配置
	 */

	/**
	 * Set the name of the parent definition of this bean definition, if any.
	 */
	void setParentName(@Nullable String parentName);

	/**
	 * Return the name of the parent definition of this bean definition, if any.
	 */
	@Nullable
	String getParentName();

	/*
		setBeanClassName/getBeanClassName 这个就是配置 Bean 的 Class 全路径，
		对应 XML 中的 <bean class=""> 配置
	 */

	/**
	 * Specify the bean class name of this bean definition.
	 * <p>The class name can be modified during bean factory post-processing,
	 * typically replacing the original class name with a parsed variant of it.
	 *
	 * @see #setParentName
	 * @see #setFactoryBeanName
	 * @see #setFactoryMethodName
	 */
	void setBeanClassName(@Nullable String beanClassName);

	/**
	 * Return the current bean class name of this bean definition.
	 * <p>Note that this does not have to be the actual class name used at runtime, in
	 * case of a child definition overriding/inheriting the class name from its parent. Also, this
	 * may just be the class that a factory method is called on, or it may even be empty in case of
	 * a factory bean reference that a method is called on. Hence, do <i>not</i> consider this to be
	 * the definitive bean type at runtime but rather only use it for parsing purposes at the
	 * individual bean definition level.
	 *
	 * @see #getParentName()
	 * @see #getFactoryBeanName()
	 * @see #getFactoryMethodName()
	 */
	@Nullable
	String getBeanClassName();

	/**
	 * Override the target scope of this bean, specifying a new scope name.
	 *
	 * @see #SCOPE_SINGLETON
	 * @see #SCOPE_PROTOTYPE
	 */
	void setScope(@Nullable String scope);

	/**
	 * Return the name of the current target scope for this bean, or {@code null} if not known yet.
	 */
	@Nullable
	String getScope();

	/*
		setLazyInit/isLazyInit 配置/获取 Bean 是否懒加载，
		这个对应了 XML 中的 <bean lazy-init=""> 配置
	 */

	/**
	 * Set whether this bean should be lazily initialized.
	 * <p>If {@code false}, the bean will get instantiated on startup by bean
	 * factories that perform eager initialization of singletons.
	 */
	void setLazyInit(boolean lazyInit);

	/**
	 * Return whether this bean should be lazily initialized, i.e. not eagerly instantiated on
	 * startup. Only applicable to a singleton bean.
	 */
	boolean isLazyInit();

	/*
		setDependsOn/getDependsOn 配置/获取 Bean 的依赖对象，
		这个对应了 XML 中的 <bean depends-on=""> 配置
	 */

	/**
	 * Set the names of the beans that this bean depends on being initialized. The bean factory will
	 * guarantee that these beans get initialized first.
	 */
	void setDependsOn(@Nullable String... dependsOn);

	/**
	 * Return the bean names that this bean depends on.
	 */
	@Nullable
	String[] getDependsOn();

	/**
	 * Set whether this bean is a candidate for getting autowired into some other bean.
	 * <p>Note that this flag is designed to only affect type-based autowiring.
	 * It does not affect explicit references by name, which will get resolved even if the specified
	 * bean is not marked as an autowire candidate. As a consequence, autowiring by name will
	 * nevertheless inject a bean if the name matches.
	 */
	void setAutowireCandidate(boolean autowireCandidate);

	/**
	 * Return whether this bean is a candidate for getting autowired into some other bean.
	 */
	boolean isAutowireCandidate();

	/**
	 * Set whether this bean is a primary autowire candidate.
	 * <p>If this value is {@code true} for exactly one bean among multiple
	 * matching candidates, it will serve as a tie-breaker.
	 */
	void setPrimary(boolean primary);

	/**
	 * Return whether this bean is a primary autowire candidate.
	 */
	boolean isPrimary();

	/**
	 * Specify the factory bean to use, if any. This the name of the bean to call the specified
	 * factory method on.
	 *
	 * @see #setFactoryMethodName
	 */
	void setFactoryBeanName(@Nullable String factoryBeanName);

	/**
	 * Return the factory bean name, if any.
	 */
	@Nullable
	String getFactoryBeanName();

	/**
	 * Specify a factory method, if any. This method will be invoked with constructor arguments, or
	 * with no arguments if none are specified. The method will be invoked on the specified factory
	 * bean, if any, or otherwise as a static method on the local bean class.
	 *
	 * @see #setFactoryBeanName
	 * @see #setBeanClassName
	 */
	void setFactoryMethodName(@Nullable String factoryMethodName);

	/**
	 * Return a factory method, if any.
	 */
	@Nullable
	String getFactoryMethodName();

	/**
	 * Return the constructor argument values for this bean.
	 * <p>The returned instance can be modified during bean factory post-processing.
	 *
	 * @return the ConstructorArgumentValues object (never {@code null})
	 */
	ConstructorArgumentValues getConstructorArgumentValues();

	/**
	 * Return if there are constructor argument values defined for this bean.
	 *
	 * @since 5.0.2
	 */
	default boolean hasConstructorArgumentValues() {
		return !getConstructorArgumentValues().isEmpty();
	}

	/**
	 * Return the property values to be applied to a new instance of the bean.
	 * <p>The returned instance can be modified during bean factory post-processing.
	 *
	 * @return the MutablePropertyValues object (never {@code null})
	 */
	/*
		返回应用于bean的新实例的属性值。可以在bean工厂后处理期间修改返回的实例。
	 */
	MutablePropertyValues getPropertyValues();

	/**
	 * Return if there are property values values defined for this bean.
	 *
	 * @since 5.0.2
	 */
	default boolean hasPropertyValues() {
		return !getPropertyValues().isEmpty();
	}

	/**
	 * Set the name of the initializer method.
	 *
	 * @since 5.1
	 */
	void setInitMethodName(@Nullable String initMethodName);

	/**
	 * Return the name of the initializer method.
	 *
	 * @since 5.1
	 */
	@Nullable
	String getInitMethodName();

	/**
	 * Set the name of the destroy method.
	 *
	 * @since 5.1
	 */
	void setDestroyMethodName(@Nullable String destroyMethodName);

	/**
	 * Return the name of the destroy method.
	 *
	 * @since 5.1
	 */
	@Nullable
	String getDestroyMethodName();

	/**
	 * Set the role hint for this {@code BeanDefinition}. The role hint provides the frameworks as
	 * well as tools with an indication of the role and importance of a particular {@code
	 * BeanDefinition}.
	 *
	 * @see #ROLE_APPLICATION
	 * @see #ROLE_SUPPORT
	 * @see #ROLE_INFRASTRUCTURE
	 * @since 5.1
	 */
	void setRole(int role);

	/**
	 * Get the role hint for this {@code BeanDefinition}. The role hint provides the frameworks as
	 * well as tools with an indication of the role and importance of a particular {@code
	 * BeanDefinition}.
	 *
	 * @see #ROLE_APPLICATION
	 * @see #ROLE_SUPPORT
	 * @see #ROLE_INFRASTRUCTURE
	 */
	int getRole();

	/**
	 * Set a human-readable description of this bean definition.
	 *
	 * @since 5.1
	 */
	void setDescription(@Nullable String description);

	/**
	 * Return a human-readable description of this bean definition.
	 */
	@Nullable
	String getDescription();

	// Read-only attributes

	/**
	 * Return whether this a <b>Singleton</b>, with a single, shared instance returned on all
	 * calls.
	 *
	 * @see #SCOPE_SINGLETON
	 */
	boolean isSingleton();

	/**
	 * Return whether this a <b>Prototype</b>, with an independent instance returned for each call.
	 *
	 * @see #SCOPE_PROTOTYPE
	 * @since 3.0
	 */
	boolean isPrototype();

	/**
	 * Return whether this bean is "abstract", that is, not meant to be instantiated.
	 */
	boolean isAbstract();

	/**
	 * Return a description of the resource that this bean definition came from (for the purpose of
	 * showing context in case of errors).
	 */
	@Nullable
	String getResourceDescription();

	/**
	 * Return the originating BeanDefinition, or {@code null} if none. Allows for retrieving the
	 * decorated bean definition, if any.
	 * <p>Note that this method returns the immediate originator. Iterate through the
	 * originator chain to find the original BeanDefinition as defined by the user.
	 */
	@Nullable
	BeanDefinition getOriginatingBeanDefinition();

}
