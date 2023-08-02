from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()

# Model to define the sign-up request payload
class SignUpData(BaseModel):
    username: str
    dob: str
    email: str
    phone: str
    password: str

# Model to define the transaction request payload
class TransactionData(BaseModel):
    amount_lent: float = 0.0
    amount_to_be_paid: float = 0.0
    total_amount: float = 0.0

# List to store the user credentials
users = []

# Endpoint to handle sign-up requests
@app.post("/signup")
def sign_up(sign_up_data: SignUpData):
    # Extract the data from the request payload
    username = sign_up_data.username
    dob = sign_up_data.dob
    email = sign_up_data.email
    phone = sign_up_data.phone
    password = sign_up_data.password

    # Implement the logic to store the sign-up data
    user = {
        "name": username,
        "dob": dob,
        "email": email,
        "phone": phone,
        "password": password,
    }
    users.append(user)

    # Return a response indicating success or failure
    return {"message": "Sign-up successful"}

# Endpoint to handle transaction requests
@app.post("/transaction")
def make_transaction(transaction_data: TransactionData):
    # Extract the data from the request payload
    amount_lent = transaction_data.amount_lent
    amount_to_be_paid = transaction_data.amount_to_be_paid
    total_amount = transaction_data.total_amount

    # Here, we are printing the data as an example
    print(f"Transaction details - Amount Lent: {amount_lent}, Amount to be Paid: {amount_to_be_paid}, Total Amount: {total_amount}")

    # Return a response indicating success or failure
    return {"message": "Transaction successful"}

