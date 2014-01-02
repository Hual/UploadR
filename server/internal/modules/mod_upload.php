<?php

class Uploader
{
	private $type;
	private $data;

	function __construct($type, $data)
	{
		$this->type = $type;
		$this->data = $data;
	}

	public function getSize()
	{
		return strlen($this->data);
	}

	public function getType()
	{
		switch($this->type)
		{
			case 'p':
			{
				return "png";
			}
			case 'j':
			{
				return "jpg";
			}
			case 'g':
			{
				return "gif";
			}
			case 't':
			{
				return "txt";
			}
			default:
			{
				return NULL;
			}
		}
	}

	public function save($type)
	{
		do
		{
			$name = substr(str_shuffle("abcefghijklmnopqrstuvwxyz-ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_"), 0, Configuration::get("name-length"));
		}
		while(file_exists("../".$name.".".$type));
		file_put_contents("../".$name.".".$type, $this->data);
		return $name.".".$type;
	}
}

?>
