<?php
define('FCM_AUTH_KEY', 'AAAAILsH8AA:APA91bGSjzYTZKfaoMlaa5Kgpf3NQOFcagQSPkhDXk_RU3Sc64EIKzD1Ch67sqQkSiczLdKU_eViNozTEHH4_4bmjM_4-rdkp0foj8jmA-tBJRAnykHbWrvqJ1O9fZXSpyfHeJKM7i20');

function sendPush($to, $title, $body, $icon, $url)
{
    $data = [
        'notification' => [
            'title' => $title,
            'body' => $body,
            'icon' => $icon,
        ],
        'to' => $to,
        'data' => [
            'click_action' => $url,
        ],
    ];

    $postdata = json_encode($data);

    $opts = [
        'http' => [
            'method' => 'POST',
            'header' => "Content-type: application/json\r\n" . "Authorization: key=" . FCM_AUTH_KEY . "\r\n",
            'content' => $postdata,
        ],
    ];

    $context  = stream_context_create($opts);

    $result = file_get_contents('https://fcm.googleapis.com/fcm/send', false, $context);

    if ($result !== false) {
        return json_decode($result);
    } else {
        return false;
    }
}

$token = $_POST['fcm_token'];

$clientPushAuthorizationKey = $token;
$notificationTitle = 'Informasi!';
$notificationText = 'Anda berhasil login!';
$notificationIcon = ''; // https://yourdomain.com/your-icon.png
$notificationClickAction = ''; // https://yourdomain.com/your-url

sendPush($clientPushAuthorizationKey, $notificationTitle, $notificationText, $notificationIcon, $notificationClickAction);
