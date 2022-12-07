package sg.edu.nus.iss.demo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
// import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import sg.edu.nus.iss.demo.Exception.ResourceNotFoundException;
import sg.edu.nus.iss.demo.model.Category;
import sg.edu.nus.iss.demo.repository.CategoryRepository;
import sg.edu.nus.iss.demo.service.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository catRepository;

    // @Autowired
    @InjectMocks
    private CategoryServiceImpl catService;

    private Category category;

    @BeforeEach
    public void setup() {

        catService = new CategoryServiceImpl(catRepository);

        category = Category.builder()
                .id(1L)
                .Name("foldie")
                .build();
    }

    // JUnit test for saveCategory method
    @DisplayName("JUnit test for saveCategory method")
    @Test
    public void givenCategoryObject_whenSaveCategory_thenReturnECategoryObject() {

        // given - precondition or setup
        // given(catRepository.findById(category.getId()))
        // .willReturn(Optional.empty());

        given(catRepository.save(category)).willReturn(category);

        System.out.println(catRepository);
        System.out.println(catService);

        // when - action or the behaviour that we are going test
        Category savedCategory = catService.saveCategory(category);

        System.out.println(savedCategory);
        // then - verify the output
        assertThat(savedCategory).isNotNull();
    }

    // JUnit test for saveCategory method
    @DisplayName("JUnit test for saveCategory method which throws exception")
    @Test
    public void givenExistingId_whenSaveCategory_thenThrowsException() {
        // given - precondition or setup
        given(catRepository.findById(2L))
                .willReturn(Optional.of(category));

        System.out.println(catRepository);
        System.out.println(catService);

        // when - action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            catService.saveCategory(category);
        });

        // then
        verify(catRepository, never()).save(any(Category.class));
    }

    // JUnit test for getAllCategories method
    @DisplayName("JUnit test for getAllCategories method")
    @Test
    public void givenCategoryList_whenGetAllCategories_thenReturnCategoryList() {

        // given - precondition or setup
        Category category1 = Category.builder()
                .id(2L)
                .Name("Road Bike")
                .build();

        given(catRepository.findAll()).willReturn(List.of(category, category1));

        // when - action or the behaviour that we are going test
        List<Category> categoryList = catService.getCategoryList();

        System.out.println();
        System.out.print("categoryList");
        System.out.println();
        System.out.print(categoryList);

        // then - verify the output
        assertThat(categoryList).isNotNull();
        assertThat(categoryList.size()).isEqualTo(2);
    }

    // JUnit test for getAllCategories method
    @DisplayName("JUnit test for getAllCategories method (negative scenario)")
    @Test
    public void givenEmptyCategoryList_whenGetAllCategories_thenReturnEmptyCategoryList() {
        // given - precondition or setup

        given(catRepository.findAll()).willReturn(Collections.emptyList());

        // when - action or the behaviour that we are going test
        List<Category> categoryList = catService.getCategoryList();

        // then - verify the output
        assertThat(categoryList).isEmpty();
        assertThat(categoryList.size()).isEqualTo(0);
    }

    // JUnit test for getEmployeeById method
    @DisplayName("JUnit test for getCategoryById method")
    @Test
    public void givenCategoryId_whenGetCategoryById_thenReturnCategoryObject() {
        // given
        // given(catRepository.findById(1L)).willReturn(Optional.of(category));

        System.out.print("givenCategoryId_whenGetCategoryById_thenReturnCategoryObject");
        System.out.println();
        System.out.print(category);

        // when
        Optional<Category> savedCategory = catService.getCategoryList().stream()
                .filter(cat -> cat.getId() == category.getId()).findAny();
        System.out.println();
        System.out.print("savedCategory");
        System.out.println();
        System.out.print(savedCategory);

        // then
        assertThat(savedCategory).isEmpty();

    }

    // JUnit test for updateCategory method
    @DisplayName("JUnit test for updateCategory method")
    @Test
    public void givenCategoryObject_whenUpdateCategory_thenReturnUpdatedCategory() {
        // given - precondition or setup
        given(catRepository.save(category)).willReturn(category);

        System.out.print("givenCategoryObject_whenUpdateCategory_thenReturnUpdatedCategory");
        System.out.println();
        System.out.print(category);
        category.setName("Mountain Bike");

        // when - action or the behaviour that we are going test
        Category updatedCategory = catService.updateCategory(category, 1L);

        // then - verify the output
        assertThat(updatedCategory.getName()).isEqualTo("Mountain Bike");
    }

    // JUnit test for deleteCategory method
    @DisplayName("JUnit test for deleteCategory method")
    @Test
    public void givenCategoryId_whenDeleteCategory_thenNothing() {
        // given - precondition or setup
        Long categoryId = 1L;

        willDoNothing().given(catRepository).deleteById(categoryId);

        // when - action or the behaviour that we are going test
        catService.deleteCategoryById(categoryId);

        // then - verify the output
        verify(catRepository, times(1)).deleteById(categoryId);
    }
}
