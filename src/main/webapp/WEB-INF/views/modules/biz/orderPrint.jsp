<%--
  Created by IntelliJ IDEA.
  User: holysky
  Date: 15/10/13
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单${orderid}</title>
    <style type="text/css" media="print">
        * {
            margin: 0;
            padding: 0;
        }
        table {
            margin: 10px;
            border: solid #000 !important;
             border-width:2px 0 0 2px !important;
            margin: 5px auto;
            width: 99%;
        }

        th, td {
            border:solid #000 !important;
            border-width:0 2px 2px 0 !important;
            padding: 3px 5px;
            text-align: center;
        }
        .fuquan{
            text-align: left;
            padding-left: 20px;
        }

        div{
            margin: 5px;
        }

        @media print {
            .no-print {
                display: none;
            }
            .page-break {
                page-break-after: always;
            }
        }
    </style>
</head>
<body>
    <table>
        <tr style="text-align: center;">
            <td colspan="2"><h2>${orderInfo.supplier.name}景区</h2></td>
            <td><h2>副券</h2></td>
        </tr>
        <tr>
            <td>游客姓名</td>
            <td>${orderInfo.customerName}</td>
            <td rowspan="5" class="fuquan">
                <div> <small>游客姓名:${orderInfo.customerName}</small> </div>
                <div> <small>联系方式:${orderInfo.customerMobile}</small> </div>
                <div> <small>门票类型:${orderInfo.proHistory.name}</small> </div>
                <div> <small>门票数量:${orderInfo.purchaseAmount} 张</small> </div>
                <div> <small>旅游日期:${orderInfo.useDate}</small> </div>
            </td>
        </tr>
        <tr>
            <td>联系方式</td>
            <td>${orderInfo.customerMobile}</td>
        </tr>
        <tr>
            <td>门票类型</td>
            <td>${orderInfo.proHistory.name}</td>
        </tr>
        <tr>
            <td>门票数量</td>
            <td>${orderInfo.purchaseAmount} 张</td>
        </tr>
        <tr>
            <td>旅游日期</td>
            <td>${orderInfo.useDate}</td>
        </tr>
        <tr>
            <td colspan="3" style="text-align: right">
                <small><em>爱游易往 www.lvxing99.com 为您旅途添油加力</em></small>
            </td>
        </tr>
    </table>
</body>
</html>
