В master ветке находится начальная версия проекта, в котором связь с БД осуществляется с помощью JDBC Template.


Update версия, с расширенным функционалом и использованием Hibernate и Spring Data JPA находится в ветке SpringDataJPA.

<hr/>
Начальный функционал позволяет:

- Выводить списки людей и книг из БД, а также добавлять в них новые сущности, изменять их и удалять, показывать "карточку" каждой по отдельности;

- В "карточке" каждой книги показывается, числится ли за ней владелец и позволяет установить его или удалить;

- В "карточке" каждого человека показывается список книг, которые за ним числятся, если таковые имеются.
