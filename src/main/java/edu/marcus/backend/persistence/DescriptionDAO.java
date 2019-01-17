package edu.marcus.backend.persistence;

import javax.enterprise.context.Dependent;

import edu.marcus.backend.persistence.model.DescriptionEntity;

/**
 * TODO: javadoc
 *
 */
@Dependent
public class DescriptionDAO {
	
	/**
	 * TODO: javadoc
	 * @return
	 */
	public DescriptionEntity retrieve() {
		return new DescriptionEntity();
	}

}
