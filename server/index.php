<?php
session_start();
define("CUSTOM_LINK_CONFIG", "./internal/config.php");
require_once "./internal/modules/mod_config.php";
require_once "./internal/modules/mod_key.php";
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
        <title>UploadR Key Request</title>
        <style type="text/css">
        body { text-align:center; }
        #change-image { font-size: 0.8em; }
        #captcha-win {
            font-family: sans-serif;
            font-size: 0.8em;
            margin-left:auto;
            margin-right:auto;
            width:220px;
            height:210px;
            border:1px solid;
            -webkit-border-radius: 3px;
            -moz-border-radius: 3px;
            border-radius: 3px; }
        </style>
    </head>
    <?php
    if (!empty($_POST['captcha']) && !empty($_SESSION['captcha']) && trim(strtolower($_POST['captcha'])) == $_SESSION['captcha'] && Configuration::get("key-generation-enabled"))
    {
        $keys = new KeyHandler("./internal/keys.php");
        do
        {
            $key = substr(str_shuffle("abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"), 0, 4).substr(str_shuffle("abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"), 0, 4);
        }
        while($keys->_keyExists($key));
        $keys->addKey($key);
        $keys->updateKeys("./internal/keys.php");
        echo '<p>Key created! Your key is:</p><br>
            <input type="text" id="key" value="'.$key.'" onClick="this.select();" /><br>
            <p>Please copy it and store it someplace safe.</p>';
    }
    else
    {
        if(Configuration::get("key-generation-enabled"))
        {
        echo '<p>Fill out the form below to get an UploadR key</p>
            <div id="captcha-win">
                <p><strong>Write the following word:</strong></p>
                <form method="POST">
                    <img src="captcha/captcha.php" id="captcha" /><br/>
                    <a href="#" onclick="document.getElementById(\'captcha\').src=\'captcha/captcha.php?\'+Math.random();document.getElementById(\'captcha-form\').focus();" id="change-image">Not readable? Change text.</a><br/><br/>
                    <input type="text" name="captcha" id="captcha-form" autocomplete="off"/><br/>
                    <input type="submit" value="Request"/>
                </form>
            </div>';
        }
        else echo "<p>Sorry! Key generation is not enabled at this time.</p>";
    }
    unset($_SESSION['captcha']);
    ?>
    </body>
</html>