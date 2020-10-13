package com.todo.app;

import com.todo.app.domain.entities.Priority;
import com.todo.app.domain.entities.Status;
import com.todo.app.domain.entities.TaskH;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.security.auth.login.Configuration;
import java.util.UUID;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.todo.app.util.JPAUtil;
import com.todo.app.command.manager.CommandExecutor;
public class App {
    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.run();
















//        int opcion = 0;
//        Scanner scanner = new Scanner(System.in);
//        TaskH producto;
//
//        EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();
//        while (opcion!=5) {
//            System.out.println("1. Crear Producto");
//            System.out.println("2. Buscar Producto");
//            System.out.println("3. Actualizar Producto");
//            System.out.println("4. Eliminar Producto");
//            System.out.println("5. Salir");
//            System.out.println("Elija una opción:");
//
//            opcion = scanner.nextInt();
//            switch (opcion) {
//                case 1:
//                    System.out.println("Digite el nombre del producto:");
//                    producto = new TaskH();
//                    producto.setId(2);
//                    scanner.nextLine();
//                    producto.setDescription(scanner.nextLine());
////                    producto.setStatus(Status.PENDING);
//
//                    System.out.println(producto);
//                    entity.getTransaction().begin();
//                    entity.persist(producto);
//                    entity.getTransaction().commit();
//                    System.out.println("Producto registrado..");
//                    System.out.println();
//                    break;

//                case 2:
//                    System.out.println("Digite el id del producto a buscar:");
//                    producto = new Producto();
//                    producto = entity.find(Producto.class, scanner.nextLong());
//                    if (producto != null) {
//                        System.out.println(producto);
//                        System.out.println();
//                    } else {
//                        System.out.println();
//                        System.out.println("Producto no encontrado... Lista de productos completa");
//                        List<Producto> listaProductos= new ArrayList<>();
//                        Query query=entity.createQuery("SELECT p FROM Producto p");
//                        listaProductos=query.getResultList();
//                        for (Producto p : listaProductos) {
//                            System.out.println(p);
//                        }
//
//                        System.out.println();
//                    }
//
//                    break;
//                case 3:
//                    System.out.println("Digite el id del producto a actualizar:");
//                    producto = new Producto();
//
//                    producto = entity.find(Producto.class, scanner.nextLong());
//                    if (producto != null) {
//                        System.out.println(producto);
//                        System.out.println("Digite el nombre del producto:");
//                        scanner.nextLine();
//                        producto.setNombre(scanner.nextLine());
//                        System.out.println("Digite el precio del producto:");
//                        producto.setPrecio(scanner.nextDouble());
//                        entity.getTransaction().begin();
//                        entity.merge(producto);
//                        entity.getTransaction().commit();
//                        System.out.println("Producto actualizado..");
//                        System.out.println();
//                    } else {
//                        System.out.println("Producto no encontrado....");
//                        System.out.println();
//                    }
//                    break;
//                case 4:
//                    System.out.println("Digite el id del producto a eliminar:");
//                    producto = new Producto();
//
//                    producto = entity.find(Producto.class, scanner.nextLong());
//                    if (producto != null) {
//                        System.out.println(producto);
//                        entity.getTransaction().begin();
//                        entity.remove(producto);
//                        entity.getTransaction().commit();
//                        System.out.println("Producto eliminado...");
//                    } else {
//                        System.out.println("Producto no encontrado...");
//                    }
//                    break;
//                case 5:entity.close();JPAUtil.shutdown();
//                    break;
//
//                default:
//                    System.out.println("Opción no válida\n");
//                    break;
//            }
//        }
  }
}