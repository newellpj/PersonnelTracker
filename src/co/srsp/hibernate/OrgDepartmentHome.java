package co.srsp.hibernate;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import co.srsp.hibernate.orm.OrgDepartment;

/**
 * Home object for domain model class OrgDepartment.
 * @see .OrgDepartment
 * @author Hibernate Tools
 */
public class OrgDepartmentHome {

	private static final Log log = LogFactory.getLog(OrgDepartmentHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(OrgDepartment transientInstance) {
		log.debug("persisting OrgDepartment instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(OrgDepartment instance) {
		log.debug("attaching dirty OrgDepartment instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(OrgDepartment instance) {
		log.debug("attaching clean OrgDepartment instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(OrgDepartment persistentInstance) {
		log.debug("deleting OrgDepartment instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public OrgDepartment merge(OrgDepartment detachedInstance) {
		log.debug("merging OrgDepartment instance");
		try {
			OrgDepartment result = (OrgDepartment) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public OrgDepartment findById(java.lang.Integer id) {
		log.debug("getting OrgDepartment instance with id: " + id);
		try {
			OrgDepartment instance = (OrgDepartment) sessionFactory.getCurrentSession().get("OrgDepartment", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(OrgDepartment instance) {
		log.debug("finding OrgDepartment instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("OrgDepartment")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
