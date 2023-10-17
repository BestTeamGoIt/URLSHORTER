Перед запуском додатку, будь ласка, встановіть наступні змінні оточення:

- `DB_USERNAME`: Логін користувача бази даних.
- `DB_PASSWORD`: Пароль користувача бази даних.



Змінні оточення можна встановити в середовищі виконання вашої програми.
Якщо ви використовуєте операційну систему типу Unix або Linux, ви можете встановити їх в терміналі перед запуском програми.
Якщо ви використовуєте операційну систему Windows, ви можете встановити їх через командний рядок.

На Unix або Linux:

Встановіть DB_USERNAME:
export DB_USERNAME=ваш_логін
Встановіть DB_PASSWORD:
export DB_PASSWORD=ваш_пароль

На Windows:
Встановіть DB_USERNAME:
set DB_USERNAME=ваш_логін
Встановіть DB_PASSWORD:
set DB_PASSWORD=ваш_пароль


Запустіть вашу програму.
Ці команди встановлять змінні оточення лише для поточної сесії. 



## Password Requirements

When registering a user, you should consider the following password requirements:

- The password must be at least 8 characters long.
- The password must include digits, uppercase, and lowercase letters.
