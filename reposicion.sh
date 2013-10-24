## acusar notificaciones

usage()
{
cat << EOF
usage: $PROG_NAME OPTIONS

OPTIONS:
   -f <factura> 	Numero de factura, valor de repo.factura
   -o <orden>		Numero de orden de servicio, valor de repo.num_orden
   -r <reimpresion>	Reimpresion de ticket de reposicion, valor 1 

EOF
}

REIMPRESION=0

F_FLAG="FALSE"
O_FLAG="FALSE"
R_FLAG="FALSE"


while getopts f:o:r: OPTION
do
    case ${OPTION} in
	f) F_FLAG=TRUE
		FACTURA=${OPTARG};;
	o) O_FLAG=TRUE
		NUMORDEN=${OPTARG};;
	r) R_FLAG=TRUE
		REIMPRESION=${OPTARG};;

      	\?) usage
           exit 2;;
    esac
done




## para validar que las opciones se hallan puesto
i=0
if [ $F_FLAG = "TRUE" ]
then
	((i=i+1))
fi

if [ $O_FLAG = "TRUE" ]
then
	((i=i+1))
fi

#echo "resultado:$i"

if [ $i -eq 0 ] 
then
#	:
	usage
	exit	
else
	:
fi


path=`dirname $0`

if [ "$path" = "." ]; then
	path=`pwd`
fi	


cd $path
pwd

export TIPO_ACUSE CONT TIPO_CARGA ACUSANDO

#echo "$TIPO_ACUSE|$CONT|$TIPO_CARGA|$ACUSANDO|"
#perl acuses.pl
cd /usr/local/soi/


perl reposicion.pl $FACTURA $NUMORDEN $REIMPRESION




