import {
    Button, Linking, TextInput, View, StyleSheet, Text, Alert, TouchableOpacity, AsyncStorage,
    Picker
} from 'react-native';
import React, {Component} from 'react';
import {Bar} from 'react-native-pathjs-charts'
import Pie from 'react-native-pie'
import PieChart from 'react-native-pie-chart';
export default class Chart extends Component<{}> {

    constructor(props) {
        super(props);
        console.log("in chart");
    };

    render() {

        // return (<View><Text>text</Text></View>);

        // const chart_wh = 250;
        // const series = [123, 321, 123, 789, 537];
        // const sliceColor = ['#F44336','#2196F3','#FFEB3B', '#4CAF50', '#FF9800'];
        //
        // return (
        // <View style={styles.container}>
        //
        //     <PieChart
        //         chart_wh={chart_wh}
        //         series={series}
        //         sliceColor={sliceColor}
        //     />
        // </View>
        //     );


        //
        // // varianta Pie
        // return (
        //     <View style={styles.myView}>
        //         <Pie
        //             radius={100}
        //             series={[10, 20, 30, 40]}
        //             colors={['red', 'lime', 'blue', 'yellow']}/>
        //     </View>
        // )


        // varianta pathjs-charts
        let ageGroups = global.novels.map(n => n.age);

        let set = new Set(ageGroups);
        let data = [];
        set.forEach(ag => {
            data.push(
                [{
                    "v": ageGroups.filter(y => y === ag).length,
                    "name": ag,
                }]);

        });

        console.log(data);

        let options = {
            width: 300,
            height: 300,
            margin: {
                top: 20,
                left: 25,
                bottom: 50,
                right: 20
            },
            color: '#2980B9',
            gutter: 20,
            animate: {
                type: 'oneByOne',
                duration: 200,
                fillTransition: 3
            },
            axisX: {
                showAxis: true,
                showLines: true,
                showLabels: true,
                showTicks: true,
                zeroAxis: false,
                orient: 'bottom',
                label: {
                    fontFamily: 'Arial',
                    fontSize: 8,
                    fontWeight: true,
                    fill: '#34495E',
                    rotate: 45
                }
            },
            axisY: {
                showAxis: true,
                showLines: true,
                showLabels: true,
                showTicks: true,
                zeroAxis: false,
                orient: 'left',
                label: {
                    fontFamily: 'Arial',
                    fontSize: 8,
                    fontWeight: true,
                    fill: '#34495E'
                }
            }
        };



        return (
            <View style={styles.myView}>
                <Bar data={data} options={options} accessorKey='v'/>
            </View>
        );


    }
};
const styles = StyleSheet.create({
    myView: {
        height: 600,
        width: 450,
        backgroundColor: '#FDEBD0'
    },
});
