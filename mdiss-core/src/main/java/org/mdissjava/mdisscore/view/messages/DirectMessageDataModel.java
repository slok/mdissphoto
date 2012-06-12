package org.mdissjava.mdisscore.view.messages;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.mdissjava.mdisscore.model.pojo.DirectMessage;
import org.primefaces.model.SelectableDataModel;

public class DirectMessageDataModel extends ListDataModel<DirectMessage>
		implements SelectableDataModel<DirectMessage> {

	public DirectMessageDataModel() {

	}

	public DirectMessageDataModel(List<DirectMessage> data) {
		super(data);
	}

	@Override
	public DirectMessage getRowData(String rowKey) {
		List<DirectMessage> messages = (List<DirectMessage>) getWrappedData();

		for (DirectMessage message : messages) {
			if (message.getId().equals(rowKey))
				return message;
		}

		return null;
	}

	@Override
	public Object getRowKey(DirectMessage message) {
		// TODO Auto-generated method stub
		return message.getId();
	}

}
