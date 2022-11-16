
function digitalClock() {
    const months = new Array(13);
    months[1]="января"; months[2]="февраля"; months[3]="марта"; months[4]="апреля";
    months[5]="мая"; months[6]="июня"; months[7]="июля"; months[8]="августа";
    months[9]="сентября"; months[10]="октября"; months[11]="ноября"; months[12]="декабря";

    let time = new Date();
    let thisMonth = months[time.getMonth() + 1];
    let date = time.getDate();
    let thisYear = time.getFullYear();
    let day = time.getDay() + 1;
``
    if (thisYear < 2000)
        thisYear = thisYear + 1900;

    let hours = time.getHours();
    let minutes = time.getMinutes();
    let seconds = time.getSeconds();

    if (hours < 10) hours = "0" + hours;
    if (minutes < 10) minutes = "0" + minutes;
    if (seconds < 10) seconds = "0" + seconds;

    document.getElementById("clock").innerHTML = getDayOfWeek(day) + ", " + date + " " + thisMonth + ", " + thisYear + " "  + hours + ":" + minutes + ":" + seconds;
    setTimeout("digitalClock()", 1000);
}

function getDayOfWeek(day){

    if (day === 1) return "Воскресенье";
    if (day === 2) return "Понедельник";
    if (day === 3) return "Вторник";
    if (day === 4) return "Среда";
    if (day === 5) return "Четверг";
    if (day === 6) return "Пятница";
    if (day === 7) return "Суббота";
}


digitalClock()