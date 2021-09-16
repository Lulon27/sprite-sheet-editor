package ui;

import java.util.Comparator;

public class LoadPriorityComparator implements Comparator<UIComponent>
{
	@Override
	public int compare(UIComponent o1, UIComponent o2)
	{
		//Higher priority comes first
		return o2.loadPriority - o1.loadPriority;
	}
}
