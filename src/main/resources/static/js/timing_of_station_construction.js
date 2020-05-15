// <span id="timeDate">载入天数...</span><span id="times">载入时分秒...</span>

var now = new Date();

function createtime() {
    var grt = new Date("06/06/2020 00:00:00");//在此处修改你的建站时间
    now.setTime(now.getTime() + 250);
    days = (now - grt) / 1000 / 60 / 60 / 24;
    dnum = Math.floor(days);
    hours = (now - grt) / 1000 / 60 / 60 - (24 * dnum);
    hnum = Math.floor(hours);
    if (String(hnum).length == 1) {
        hnum = "0" + hnum;
    }
    minutes = (now - grt) / 1000 / 60 - (24 * 60 * dnum) - (60 * hnum);
    mnum = Math.floor(minutes);
    if (String(mnum).length == 1) {
        mnum = "0" + mnum;
    }
    seconds = (now - grt) / 1000 - (24 * 60 * 60 * dnum) - (60 * 60 * hnum) - (60 * mnum);
    snum = Math.round(seconds);
    if (String(snum).length == 1) {
        snum = "0" + snum;
    }
    document.getElementById("timeDate").innerHTML = "本站已侥幸存活 " + dnum + " 天 ";
    document.getElementById("times").innerHTML = hnum + " 小时 " + mnum + " 分 " + snum + " 秒";
}

setInterval("createtime()", 250);