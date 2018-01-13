import React, {Component} from 'react';
import {
    AppRegistry, Text, View, StyleSheet, Button, Alert, ListView, TouchableOpacity,
    TouchableHighlight, AsyncStorage
} from 'react-native'
import {ConfirmDialog} from 'react-native-simple-dialogs';

export default class Main extends Component<{}> {
    constructor(props) {
        super(props);
        console.log("am intrat");
        let novels = [];

        let dataSource = new ListView.DataSource({rowHasChanged: (r1, r2) => r1.Id !== r2.Id});
        this.state = {
            dataSource: dataSource.cloneWithRows(novels),
            dialogVisible: false
        };
        AsyncStorage.getItem('novels').then((value) => {
            novels = JSON.parse(value);
            global.novels = novels;
            this.state.dataSource = dataSource.cloneWithRows(novels);
            this.setState(this.state);
        }).catch((error) => {
            console.log("error");
            alert(error.message);
        });
        // // let dataSource = new ListView.DataSource({rowHasChanged: (r1, r2) => r1.Id !== r2.Id});
        // this.state = {
        //     dataSource: dataSource.cloneWithRows(novels),
        //     dialogVisible: false


    }

    goToDetail(novel) {
        this.props.navigation.navigate("NovelDetail", novel);
    }

    showDialog(novel) {
        this.state.dialogVisible = true;
        this.state.selectedNovel = novel;
        this.setState(this.state);

    }

    remove(novel) {
        this.state.dialogVisible = false;
        console.log("remove");
        global.novels.splice(global.novels.indexOf(novel), 1);

        let dataSource = new ListView.DataSource({rowHasChanged: (r1, r2) => r1.Id !== r2.Id});
        this.state.dataSource = dataSource.cloneWithRows(global.novels);
        this.setState(this.state);

        AsyncStorage.setItem('novels', JSON.stringify(global.novels));

    }

    create() {
        this.props.navigation.navigate("Create");
    }

    goToChart() {
        console.log(global.novels);
        this.props.navigation.navigate("Chart",global.novels);
    }
    renderRow(novel) {
        return (
            <TouchableHighlight onPress={() => this.goToDetail(novel)}
                                onLongPress={() => this.showDialog(novel)}>
                <View>
                    <Text style={styles.listItem}>{novel.title}</Text>
                </View>
            </TouchableHighlight>
        );
    }


    render() {
        return (
            <View style={styles.myView}>
                <ListView style={styles.myList}
                          dataSource={this.state.dataSource}
                          renderRow={this.renderRow.bind(this)}
                          enableEmptySections={true}/>
                <View>
                    <ConfirmDialog
                        title="Confirm Dialog"
                        message="Are you sure you want to delete this novel?"
                        visible={this.state.dialogVisible}
                        onTouchOutside={() => this.setState({dialogVisible: false})}
                        positiveButton={{
                            title: "YES",
                            onPress: () => this.remove(this.state.selectedNovel)
                        }}
                        negativeButton={{
                            title: "NO",
                            onPress: () => {
                                this.state.dialogVisible = false;
                                this.setState(this.state)
                            }
                        }}
                    />
                </View>
                <View style={styles.addButton}>
                    <TouchableOpacity onPress={() => {
                        this.create()
                    }}>
                        <View>
                            <Text style={styles.btnText}>+</Text>
                        </View>
                    </TouchableOpacity>
                </View>
                <View style={styles.chartButton}>
                    <TouchableOpacity onPress={() => {
                        this.goToChart()
                    }}>
                        <View>
                            <Text style={styles.btnText}>See chart</Text>
                        </View>
                    </TouchableOpacity>
                </View>

            </View>
        );
    }
};

const styles = StyleSheet.create({
    myView: {
        height: 600,
        flex: 1,
        flexDirection: 'row'
    },
    myText: {
        marginTop: 60,
        fontSize: 60,
        textAlign: 'center',
        color: '#48C9B0'
    },
    myList: {
        marginTop: 30,
        marginLeft: 30,
    },
    listItem: {
        marginTop: 10,
        fontSize: 20,
        color: '#48C9B0'
    },
    navBar: {
        flex: 1,
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: 'red', // changing navbar color
    },
    navTitle: {
        color: 'white', // changing navbar title color
    },
    addButton: {
        height: 50,
        width: 50,
        position: 'absolute',
        right: 10,
        top: 30,
        // bottom:0,
        // left:0,
        backgroundColor: '#48C9B0',
        borderRadius: 25,
        // shadowOffset: {
        //     width:0,
        //     height:3
        // },
        // shadowRadius:10,
        // shadowOpacity:0.25
    },
    btnText: {
        color: 'white',
        fontSize: 32,
        fontWeight: 'bold',
        textAlign: 'center'
    },

    chartButton: {
        position: 'absolute',
        left: 0,
        right: 0,
        bottom: 0,
        height: 40,
        backgroundColor: '#48C9B0',

    }
});
