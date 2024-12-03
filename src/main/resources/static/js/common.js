// 作成ボタンのクリックイベント
document.getElementById('createTaskBtn').addEventListener('click', function(event) {
    event.preventDefault();  // フォーム送信を無効にする
    const title = document.getElementById('taskTitle').value;

    if (!title) {
        alert("タスクタイトルを入力してください");
        return;
    }

    // fetchでタスクを送信
    fetch('http://localhost:8080/api/Task', { 
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ title: title })  // タイトルをJSON形式で送信
    })
    .then(response => response.json())
    .then(result => {
        if (result && result.title) {
            alert('タスクが作成されました');
              // 作成後にToDoリストを更新
              for(let i = 1; i <= 4; i++){
                findByCategoryId(i)
              }
        } else {
            alert('タスク作成に失敗しました');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('エラーが発生しました');
    });
});

// タスク編集
function editTask(task) {
    // 編集用のフォームを表示
    const newTitle = prompt("新しいタスク名を入力してください:", task.title);

    // カテゴリの選択肢を作成
    const categoryId = prompt("カテゴリーを選択してください（1: ToDo, 2: 作業中, 3: 確認待ち, 4: 完了）:", task.category_id);

    if (newTitle && categoryId) {
        // APIを呼び出して、タスクを更新
        fetch(`http://localhost:8080/api/Task/${task.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                title: newTitle,
                category_id: parseInt(categoryId)  // カテゴリIDを整数として送信
            })
        })
        .then(response => response.json())
        .then(data => {
            alert('タスクが更新されました');
            for(let i = 1; i <= 4; i++){
                findByCategoryId(i)
              }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('タスク更新に失敗しました');
        });
    }
}

// タスクを表示する関数の中でカテゴリー列の表示を制御
function findByCategoryId(taskId) {
    // APIを呼び出してタスクを取得
    fetch(`http://localhost:8080/api/Task/${taskId}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        },
    }) 
    .then(response => response.json()) // レスポンスをJSON形式で取得
    .then(tasks => {
        const todoList = document.getElementById(`todoList${taskId}`);
        todoList.innerHTML = '';  // 既存のリストをクリア
        const row = document.createElement('tr');
        const th = document.createElement('th');
        th.textContent = 'To Do'
        row.appendChild(th);
        row.setAttribute('colspan',3)
        todoList.appendChild(row);

        // タスクがない場合のメッセージ表示
        if (tasks.length === 0) {
            const row = document.createElement('tr');
            const cell = document.createElement('td');
            cell.setAttribute('colspan', 3);
            cell.textContent = 'タスクはありません';
            row.appendChild(cell);
            todoList.appendChild(row);
            return;
        }

        // タスクを表示
        tasks.forEach(task => {
            const row = document.createElement('tr');
            
            // タスクタイトルを表示するセル
            const titleCell = document.createElement('td');
            titleCell.textContent = task.title;  // タスクタイトルを表示
            row.appendChild(titleCell);

            // カテゴリーを表示するセル
            const categoryCell = document.createElement('td');
            const categoryName = getCategoryName(task.category_id); // カテゴリー名を取得
            categoryCell.textContent = categoryName;  // カテゴリー名を表示
            if (categoryName === '') {
                categoryCell.style.display = 'none';  // カテゴリー名が空の場合、その列を非表示にする
            }
            row.appendChild(categoryCell);

            // 編集ボタンの追加
            const editCell = document.createElement('td');
            const editButton = document.createElement('button');
            editButton.textContent = '編集';
            editButton.onclick = function() {
                editTask(task);  // 編集ボタンがクリックされたときに呼ばれる
            };
            editCell.appendChild(editButton);
            row.appendChild(editCell);

            // 削除ボタンの追加
            const deleteCell = document.createElement('td');
            const deleteButton = document.createElement('button');
            deleteButton.textContent = '削除';
            deleteButton.onclick = function() {
                deleteTask(task.id);  // 削除ボタンがクリックされたときに呼ばれる
                // 作成後にToDoリストを更新
              
            };
            deleteCell.appendChild(deleteButton);
            row.appendChild(deleteCell);

            todoList.appendChild(row);  // 新しいタスク行を追加
        });
    })
    .catch(error => {
        console.error('Error:', error);
        alert('ToDoリストの取得に失敗しました');
    });
}

// カテゴリーIDを元にカテゴリー名を取得する関数
function getCategoryName(categoryId) {
    switch (categoryId) {
        case 1: return 'ToDo';
        case 2: return '作業中';
        case 3: return '確認待ち';
        case 4: return '完了';
        default: return '';  // 未分類の場合は空文字を返す
    }
}

// タスク削除
function deleteTask(taskId) {
    fetch(`http://localhost:8080/api/Task/${taskId}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            alert('タスクが削除されました');
        } else {
            alert('タスク削除に失敗しました');
        }

        for(let i = 1; i <= 4; i++){
            findByCategoryId(i)
          }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('タスク削除に失敗しました');
    });
}