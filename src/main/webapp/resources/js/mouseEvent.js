document.getElementById("area").onmouseup = function (event){
        let r = checkR()
        if(r.status) {
                let x = parseFloat((r.val * (event.offsetX - 200) / 120).toFixed(3))
                let y = parseFloat((r.val * (140 - event.offsetY) / 120).toFixed(3))
                document.getElementById("j_idt50:hidden-x").value = x
                document.getElementById("j_idt50:hidden-y").value = y
                document.getElementById("j_idt50:hidden-r").value = r.val
                document.getElementById("j_idt50:hidden-form").click()

                chooseGraph(x, y, r.val)
        }
        else{
                alert("Выберете число R")
        }

}