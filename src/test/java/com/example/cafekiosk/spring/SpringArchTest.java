package com.example.cafekiosk.spring;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packagesOf = CafekioskApplication.class)
public class SpringArchTest {

    @ArchTest
    ArchRule domainLayerPackageRule = classes().that().resideInAPackage("..domain..")
                                               .should()
                                               .onlyBeAccessed()
                                               .byClassesThat()
                                               .resideInAnyPackage("..api..", "..domain..");

    @ArchTest
    ArchRule businessLayerPackageRule = classes().that().resideInAPackage("..api.service..")
                                                 .should()
                                                 .onlyBeAccessed()
                                                 .byClassesThat()
                                                 .resideInAnyPackage("..api.service..", "..api.controller..");

    @ArchTest
    ArchRule freeOfCycles = slices().matching("..api.service.(*)..")
                                    .should()
                                    .beFreeOfCycles();
}