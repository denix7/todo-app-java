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

  }
}