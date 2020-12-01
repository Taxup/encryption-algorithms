import java.math.BigInteger

fun main(args: Array<String>) {
    print("Enter q - "); val q = BigInteger(readLine())

    print("Enter a - "); var a = BigInteger(readLine())
    while (true){
        val arr: Array<Int> = Array(q.toInt()-1,{0})
        for (i in 0 until q.toInt()-1){
            arr[i] = a.pow(i).mod(q).toInt()
        }
        arr.sort()
        var check = 0
        for (i in 0 until q.toInt()-1){
            if (!arr[i].equals(i+1)){
                break
            }else{
                check++
            }
        }
        if (check==q.toInt()-1)break
        print("a is not primitive root of q\nEnter a - "); a = BigInteger(readLine())
    }

    print("Enter Xa - "); val xa = readLine()!!.toInt()
    print("Enter Xb - "); val xb = readLine()!!.toInt()
//    var xa =0
//    for (i in 0..30){
//        if (a.pow(i).mod(q).toInt()==21){
//            xa = i
//            break
//        }
//    }

    val ya = a.pow(xa).mod(q)
//    val ya = BigInteger("21")
    val yb = a.pow(xb).mod(q)

    val kab1 = yb.pow(xa).mod(q)
    val kab2 = ya.pow(xb).mod(q)


    println("K  -  $kab1")
    println(kab1==kab2)

    print("xa = $xa   ya = $ya\nxb = $xb   yb = $yb")
}

//Enter q - 31
//Enter a - 3
//Enter Xb - 17
//K  -  24
//true
//xa = 29   ya = 21
//xb = 17   yb = 22