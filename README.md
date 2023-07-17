# Swagger

Your task is:
- add required dependencies
- create model `Product` with fields
    - id
    - title
    - price
- create `ProductRepository`
- create `ProductService`
- create `ProductController`. Via the controller you must be able to:
    - create a new Product
    - get Product by ID
    - delete Product by ID
    - update Product
    - get all products with pagination and ability to sort by price or by title in ASC or DESC order
    - get all products where price is between two values received as a `RequestParam` inputs. 
        Add pagination and ability to sort by price or by title in ASC or DESC order.
- create required DTOs and mappers
- add swagger and proper documentation over the controller endpoints
