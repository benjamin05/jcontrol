
PROG_NAME=$(basename $0)

## variables de validacion
S_FLAG=FALSE
C_FLAG=FALSE

entregar=1

usage()
{
cat << EOF
usage: $PROG_NAME OPTIONS

OPTIONS:
   -s <id_sucursal externo> numero de id_sucursal externo
   -c <id_cliente> numero de id_cliente externo a importar

EOF
}

while getopts s:c: OPTION
do
    case ${OPTION} in
        s) S_FLAG=TRUE
		ID_SUC=${OPTARG};;
        c) C_FLAG=TRUE
		ID_CLI=${OPTARG};;

      	\?) usage
           exit 2;;
    esac
done

i=0

if [ $C_FLAG = "FALSE" ]
then
	((i=i+1))
fi

if [ $S_FLAG = "FALSE" ]
then
	((i=i+1))
fi



if [ $i -eq 0 ]
then
	:
#	usage
#	exit
else
#	:
	usage
	exit
fi



	## variables con valores por default
	conf=/usr/local/Jsoi/soi.conf
	script=importar_cliente.pl
	soi_path=/usr/local/soi
	log=/home/paso/${LOGNAME}.importar_cliente.log


	## leer variables de configuracion si existe soi.conf
	if test -f ${conf}
	then
		while read line
		do
			if [ -n "$line" ]
			then

				cont=`echo -e $line | sed 's/ //g'`

				key=`echo $cont | cut -d= -f1`
				value=`echo $cont | cut -d= -f2`

				export ${key}=${value}
			fi

		done < ${conf}
	fi


	cd ${soi_path}

	echo "----------------------------------------------------" >> ${log}
	date >> ${log}

	perl ${script} ${ID_SUC} ${ID_CLI} >> ${log} 2>> ${log}






