package org.bossky.jpa.support;

import org.bossky.jpa.Persistent;
import org.bossky.jpa.Repository;
import org.bossky.jpa.UniteId;

public class SimpleRepository implements Repository<Persistent> {

	@Override
	public Persistent get(UniteId id) {
		if (null == id) {
			return null;
		}
		return get(id.getOrdinal());
	}

	@Override
	public Persistent get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Persistent v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Persistent v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Persistent v) {
		// TODO Auto-generated method stub

	}

}
