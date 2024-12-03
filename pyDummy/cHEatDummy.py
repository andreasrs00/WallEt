import firebase_admin
from firebase_admin import credentials, firestore
import random
from datetime import datetime, timedelta

cred = credentials.Certificate("-")
firebase_admin.initialize_app(cred)
db = firestore.client()

fixed_uuid = "IDNYA"

def random_time_in_day(base_date):
    hour = random.randint(0, 23)
    minute = random.randint(0, 59)
    second = random.randint(0, 59)
    return base_date.replace(hour=hour, minute=minute, second=second)

categories_data = {
    "Food": ["Makan Pagi", "Makan Siang", "Makan Malam"],
    "Transport": ["Bensin", "Taksi", "Bus"],
    "Medicine": ["Beli Obat", "Cek Dokter", "Vitamin"],
    "Groceries": ["Belanja Bulanan", "Beli Sayur", "Beli Buah"],
    "Rent": ["Sewa Apartemen", "Sewa Rumah", "Bayar Kontrakan"],
    "Gift": ["Hadiah Teman", "Kado Ulang Tahun", "Hadiah Pernikahan"],
    "Savings": ["Tabungan Bulanan", "Investasi", "Deposito"],
    "Entertainment": ["Nonton Bioskop", "Nonton Bola", "Langganan Netflix"]
}

def generate_dummy_data():
    start_date = datetime.now() - timedelta(days=40)  

    for day in range(40):  
        current_date = start_date + timedelta(days=day)
        for _ in range(20):  
            category = random.choice(list(categories_data.keys()))
            
            if category == "Savings":
                transaction_type = "income"
            else:
                transaction_type = random.choice(["income", "expense"])

            title_options = categories_data[category]
            expense_title = random.choice(title_options)

            doc_ref = db.collection("Transaction").document(fixed_uuid) \
                        .collection("categories").document(category) \
                        .collection(transaction_type).document()

            dummy_data = {
                "Amount": random.randint(10000, 1000000),
                "Category": category,
                "Date": random_time_in_day(current_date).isoformat(),  
                "Expense Title": expense_title,
                "Type": "Income" if transaction_type == "income" else "Expense"
            }

            doc_ref.set(dummy_data)

generate_dummy_data()
print("Dummy data berhasil dibuat dengan judul sesuai kategori!")
