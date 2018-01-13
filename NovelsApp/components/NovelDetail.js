import {Button, Linking, TextInput, View, StyleSheet, Text, Alert, TouchableOpacity, AsyncStorage} from 'react-native';
import React, {Component} from 'react';

export default class NovelDetail extends Component<{}> {

    constructor(props) {
        super(props);

        // this.state={}
        this.state = {
            id: 0,
            title: "",
            author: "",
            content: ""
        };

        if (this.props.navigation.state.params.id !== undefined) {
            let currentNovel = this.props.navigation.state.params;
            this.state.id = currentNovel.id;
            this.state.title = currentNovel.title;
            this.state.author = currentNovel.author;
            this.state.content = currentNovel.content;

        }

        // this._onPress = this._onPress.bind(this);


    }

    edit() {
        let novel = this.state;
        for (let i = 0; i < global.novels.length; i++) {
            if (global.novels[i].id === novel.id) {
                global.novels[i] = novel;
                break;
            }
        }
        AsyncStorage.setItem('novels',JSON.stringify(global.novels)).then(() => {
            this.props.navigation.navigate('Home');
        }).catch((error)=>{
            console.log("error");
            alert(error.message);
        });
    }

    render() {
        return (
            <View style={styles.myView}>
                <Text style={styles.myText}>Edit novel</Text>
                <View style={styles.inputForm}>

                    <TextInput style={styles.inputText}
                               editable={false}
                               value={this.state.title}/>

                    <TextInput style={styles.inputText}
                               editable={false}
                               value={this.state.author}/>

                    <TextInput style={styles.inputText1}
                               onChangeText={(text) => this.setState({content: text})}
                               value={this.state.content}
                                multiline={true}
                               numberOfLines = {4}/>

                </View>
                <TouchableOpacity onPress={() => {this.edit()}}>
                    <View style={styles.myButton}>
                        <Text style={styles.btnText}>Save</Text>
                    </View>
                </TouchableOpacity>
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
    btnText: {
        textAlign: 'center',
        color: 'white',
        fontSize: 20,
    },
    myText: {
        marginTop: 20,
        marginLeft: 60,
        fontSize: 50,
        color: '#c987ba'
    },
    inputText: {
        marginTop: 10,
        marginLeft:50,
        height: 40,
        width: 300,
        backgroundColor: '#c9b0c7',
        color: 'white',
    },
    inputForm:{
        marginTop:20
    },
    myButton: {
        height: 50,
        width: 100,
        marginTop: 30,
        marginLeft: 150,
        backgroundColor: '#c9b0c7',
        borderRadius: 20,
        padding: 10,
        // shadowOffset: {
        //     width:0,
        //     height:3
        // },
        // shadowRadius:10,
        // shadowOpacity:0.25
    },
    inputText1: {
        marginTop: 10,
        marginLeft:50,
        // height: 40,
        width: 300,
        backgroundColor: '#c987ba',
        color: 'white',
    },
    myList: {
        marginTop: 50
    },
    listItem: {
        marginTop: 10,
        fontSize: 20,
        color: '#c987ba'
    }
});
