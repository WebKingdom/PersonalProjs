import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';

class VoTHomePage extends StatefulWidget {
  @override
  VoTHomeState createState() => VoTHomeState();
}

class VoTHomeState extends State<VoTHomePage> {
  String _numWeeks = "N/A";

  final _hrWageCtrl = TextEditingController();
  final _hrsPWeekCtrl = TextEditingController();
  final _wkSpendingCtrl = TextEditingController();
  final _withholdingsCtrl = TextEditingController();
  final _taxCtrl = TextEditingController();
  final _goalCtrl = TextEditingController();

  void _calcTime() {
    setState(() {
      double wkNetIncome = (double.parse(_hrWageCtrl.text) * double.parse(_hrsPWeekCtrl.text)) * (1.0 - (double.parse(_taxCtrl.text) / 100.0));
      double wkAfterSpend = wkNetIncome - double.parse(_wkSpendingCtrl.text) - double.parse(_withholdingsCtrl.text);
      _numWeeks = (double.parse(_goalCtrl.text) / wkAfterSpend).toStringAsFixed(2);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Value of Time'),
      ),
      backgroundColor: Colors.white,
      body: Container(
        padding: const EdgeInsets.all(10.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          verticalDirection: VerticalDirection.down,
          children: <Widget>[
            TextFormField(
              controller: _goalCtrl,
              keyboardType: TextInputType.number,
//              inputFormatters: <TextInputFormatter> [
//                WhitelistingTextInputFormatter.digitsOnly
//              ],
              decoration: InputDecoration(
                labelText: "GOAL",
                hintText: "amount",
                icon: Icon(Icons.attach_money),
              ),
            ),
            Spacer(
              flex: 1,
            ),
            Text("You will need $_numWeeks weeks."),
            Spacer(
              flex: 2,
            ),
            TextFormField(
              controller: _hrWageCtrl,
              keyboardType: TextInputType.number,
//              inputFormatters: <TextInputFormatter> [
//                WhitelistingTextInputFormatter.digitsOnly
//              ],
              decoration: InputDecoration(
                labelText: "Hourly wage:",
                hintText: "amount",
                icon: Icon(Icons.account_balance_wallet),
              ),
            ),
            TextFormField(
              controller: _hrsPWeekCtrl,
              keyboardType: TextInputType.number,
//              inputFormatters: <TextInputFormatter> [
//                WhitelistingTextInputFormatter.digitsOnly
//              ],
              decoration: InputDecoration(
                labelText: "Hours/week",
                hintText: "hours",
                icon: Icon(Icons.access_time),
              ),
            ),
            TextFormField(
              controller: _wkSpendingCtrl,
              keyboardType: TextInputType.number,
//              inputFormatters: <TextInputFormatter> [
//                WhitelistingTextInputFormatter.digitsOnly
//              ],
              decoration: InputDecoration(
                labelText: "Weekly spending",
                hintText: "amount",
                icon: Icon(Icons.attach_money),
              ),
            ),
            TextFormField(
              controller: _withholdingsCtrl,
              keyboardType: TextInputType.number,
//              inputFormatters: <TextInputFormatter> [
//                WhitelistingTextInputFormatter.digitsOnly
//              ],
              decoration: InputDecoration(
                labelText: "Withholdings",
                hintText: "amount",
                icon: Icon(Icons.attach_money),
              ),
            ),
            TextFormField(
              controller: _taxCtrl,
              keyboardType: TextInputType.number,
//              inputFormatters: <TextInputFormatter> [
//                WhitelistingTextInputFormatter.digitsOnly
//              ],
              decoration: InputDecoration(
                labelText: "Tax",
                hintText: "percent",
                icon: Icon(Icons.account_balance),
              ),
            ),
            Spacer(
              flex: 6,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _calcTime,
        tooltip: "Calculate",
        child: Icon(Icons.all_inclusive),
      ),
    );
  }
}
