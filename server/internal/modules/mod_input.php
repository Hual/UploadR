<?php

class InputHandler
{
	private $input = NULL;
	private $validHeaders = array("u", "k", "i");

	function __construct()
	{
		$this->input = file_get_contents("php://input");
	}

	public function isUserAgentValid()
	{
		return (strpos($_SERVER['HTTP_USER_AGENT'], "UploadR Client/") === 0);
	}

	public function getValidHeaders()
	{
		return $this->validHeaders;
	}

	public function hasRequiredLength()
	{
		return (strlen($this->input) > 0);
	}

	public function hasRequiredUploadLength()
	{
		return (strlen($this->input) > 2);
	}

	public function hasValidHeader()
	{
		return in_array($this->input{0}, $this->validHeaders);
	}

	public function hasKey()
	{
		return ($this->input{1} === '1');
	}

	public function isKeyValid()
	{
		return (strlen($this->input) > 18);
	}

	public function getKey()
	{
		return substr($this->input, 2, 16);
	}

	public function getHeader()
	{
		return $this->input{0};
	}

	public function getData()
	{
		return substr($this->input, ($this->hasKey() ? (19) : (3)));
	}

	public function getType()
	{
		return ($this->hasKey() ? ($this->input{18}) : ($this->input{2}));
	}
}

?>