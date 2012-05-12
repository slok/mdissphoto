package org.mdissjava.mdisscore.model.dao;

import java.util.List;
import org.mdissjava.mdisscore.model.pojo.Tag;

	public interface TagDao {

		void insertTag(Tag tag);
		List<Tag> findTag(Tag tag);
		void updateTag(Tag tag);
		void deleteTag(Tag tag);
	}


