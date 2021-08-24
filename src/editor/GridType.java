package editor;

public enum GridType
{
	SPRITESHEET("Sprite sheet"),
	TILESET("Tile set");
	
	private String name;
	
	private GridType(String name)
	{
		this.name = name;
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
}
