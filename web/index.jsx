import React from 'react';
import { message, Button, Input, Pagination, Popconfirm, Radio, Table } from 'antd';

const serverUrl = '/api';

const ContentStyle = {
    width: 1080,
    padding: 20,
    display: 'flex',
    flexDirection: 'row',
    flexWrap: 'wrap',
    flexShrink: 0,
};

const ContentBlock = {
    width: 480,
    margin: 20,
    padding: 20,
    borderRadius: 8,
    border: '1px solid #40a9ff',
    display: 'flex',
    flexDirection: 'column',
};

const Field = ({label, field, password, user, setUser, children}) => {
    return (
        <div style={{display: 'flex', alignItems: 'center', marginBottom: 20}} key={label}>
            <span style={{marginRight: 20, width: 42, flexShrink: 0}}>{label}</span>
            {
                children || (
                    password ?
                        <Input.Password value={user[field] || ''}
                                        onChange={e => {
                                            setUser({...user, [field]: e.target.value});
                                        }}/> :
                        <Input value={user[field] || ''}
                               onChange={e => {
                                   setUser({...user, [field]: e.target.value});
                               }}/>
                )
            }
        </div>
    );
};

const CreateBlock = () => {
    const [user, setUser] = React.useState({gender: 0});

    const onSubmit = () => {
        console.log(user);
        if (!user.username) {
            return message.error('请输入用户名', 1);
        }
        if (!user.password) {
            return message.error('请输入密码', 1);
        }
        if (!user.email) {
            return message.error('请输入邮箱', 1);
        }
        fetch(`${serverUrl}/user/create`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(user),
        }).then(resp => {
            message.info(`status: ${resp.status}`, 1);
            return resp.json();
        }).then(data => {
            message.info(`response: ${JSON.stringify(data)}`, 1);
            setUser({gender: 0});
        });
    };

    return (
        <div style={ContentBlock}>
            <h3>新建用户</h3>
            <Field label='用户名' field='username' user={user} setUser={setUser}/>
            <Field label='密码' password field='password' user={user} setUser={setUser}/>
            <Field label='性别' field='gender'>
                <Radio.Group onChange={e => setUser({...user, gender: e.target.value})} value={user?.gender}>
                    <Radio value={0}>Male</Radio>
                    <Radio value={1}>Female</Radio>
                </Radio.Group>
            </Field>
            <Field label='邮箱' field='email' user={user} setUser={setUser}/>
            <Button onClick={onSubmit}>创建</Button>
        </div>
    );
}

const QueryBlock = () => {
    const [user, setUser] = React.useState({gender: 0});
    const [id, setId] = React.useState('');

    const onSearch = () => {
        console.log(id);
        if (!id) {
            return message.error('请输入ID', 1);
        }
        fetch(`${serverUrl}/user/get?id=${id}`).then(resp => {
            message.info(`status: ${resp.status}`, 1);
            return resp.json();
        }).then(data => {
            message.info(`response: ${JSON.stringify(data)}`, 1);
            setUser(data);
        });
    };

    const onUpdate = () => {
        console.log(user);
        if (!id) {
            return message.error('请输入ID', 1);
        }
        if (!user.username) {
            return message.error('请输入用户名', 1);
        }
        if (!user.password) {
            return message.error('请输入密码', 1);
        }
        if (!user.email) {
            return message.error('请输入邮箱', 1);
        }
        fetch(`${serverUrl}/user/update?id=${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(user),
        }).then(resp => {
            message.info(`status: ${resp.status}`, 1);
            return resp.json();
        }).then(data => {
            message.info(`response: ${JSON.stringify(data)}`, 1);
            setUser(data);
        });
    };

    const onDelete = () => {
        console.log(id);
        if (!id) {
            return message.error('请输入ID');
        }
        fetch(`${serverUrl}/user/delete?id=${id}`, {
            method: 'DELETE',
        }).then(resp => {
            message.info(`status: ${resp.status}`, 1);
            return resp.text();
        }).then(data => {
            message.info(`response: ${data}`, 1);
            setUser({gender: 0});
            setId('');
        });
    };

    return (
        <div style={ContentBlock}>
            <h3>查询/修改/删除用户</h3>
            <Field label='ID' field='id'>
                <Input value={id}
                       onChange={e => setId(e.target.value)}/>
                <Button style={{marginLeft: 20}} onClick={onSearch}>查询</Button>
            </Field>
            <Field label='用户名' field='username' user={user} setUser={setUser}/>
            <Field label='密码' password field='password' user={user} setUser={setUser}/>
            <Field label='性别' field='gender'>
                <Radio.Group onChange={e => setUser({...user, gender: e.target.value})} value={user?.gender}>
                    <Radio value={0}>Male</Radio>
                    <Radio value={1}>Female</Radio>
                </Radio.Group>
            </Field>
            <Field label='邮箱' field='email' user={user} setUser={setUser}/>
            <div style={{display: 'flex'}}>
                <Button onClick={onUpdate} style={{flexGrow: 1}}>修改</Button>
                <Popconfirm title='确定要删除用户吗？'
                            onConfirm={onDelete}
                            onCancel={() => {}}
                            okText='删除'
                            cancelText='取消'>
                    <Button style={{marginLeft: 20, flexGrow: 1}}>删除</Button>
                </Popconfirm>
            </div>
        </div>
    );
}

const ListBlock = () => {
    const [users, setUsers] = React.useState([]);
    const [total, setTotal] = React.useState(0);
    const pageSize = 8;
    const [pageNo, setPageNo] = React.useState(0);

    React.useEffect(() => getPage(), []);

    const getPage = (page=0) => {
        fetch(`${serverUrl}/user/count`).then(resp => {
            return resp.json();
        }).then(data => {
            setTotal(data);
        });
        fetch(`${serverUrl}/user/list?pageSize=${pageSize}&pageNo=${page}`).then(resp => {
            return resp.json();
        }).then(data => {
            console.log(data)
            setUsers(data);
            setPageNo(page);
        });
    }

    const columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: '用户名',
            dataIndex: 'username',
            key: 'username',
        },
        {
            title: '性别',
            dataIndex: 'gender',
            key: 'gender',
            render: gender => gender ? 'Female' : 'Male',
        },
        {
            title: '邮箱',
            dataIndex: 'email',
            key: 'email',
        },
    ];

    return (
        <div style={{...ContentBlock, width: 1000}}>
            <h3>用户列表</h3>
            <h4>总用户：{total} <Button onClick={() => getPage()} size="small" style={{marginLeft: 10}}>Sync</Button></h4>
            <Table dataSource={users} columns={columns} rowKey='id' pagination={false}/>
            <Pagination current={pageNo + 1}
                        total={total}
                        onChange={page => getPage(page - 1)}
                        showSizeChanger={false}
                        style={{marginTop: 20}}/>
        </div>
    );
}

export default class Index extends React.Component {

    render() {
        return (
            <div style={ContentStyle}>
                <h1 style={{width: '100%', textAlign: 'center'}}>spring-boot-exercise 测试</h1>
                <CreateBlock/>
                <QueryBlock/>
                <ListBlock/>
            </div>
        );
    }
}