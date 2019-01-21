package edu.marcus.backend.persistence;

import javax.enterprise.context.Dependent;

import edu.marcus.backend.persistence.model.DescriptionEntity;

/**
 * DAO responsible for access description data
 */
@Dependent
public class DescriptionDAO {
	
	/**
	 * Retrieves description entity
	 * @return description entity
	 */
	public DescriptionEntity retrieve() {
		return new DescriptionEntity();
	}

}
