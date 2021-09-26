package cc.xuepeng.ray.framework.sdk.mgt.service.impl.formatter;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysDept;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门格式化器。
 *
 * @author xuepeng
 */
@Service("sysDeptLevelFormatter")
public class SysDeptLevelFormatterImpl implements SysDeptLevelFormatter {

    /**
     * 格式化部门。
     *
     * @param sysDepts 部门信息。
     * @return 部门信息。
     */
    public List<SysDept> format(final List<SysDept> sysDepts) {
        final List<SysDept> roots = sysDepts.stream()
                .filter(department -> department.getDeptPid() == 0)
                .collect(Collectors.toList());
        roots.forEach(root -> createSysDeptTree(root, sysDepts));
        return roots;
    }

    /**
     * 递归创建部门层级。
     *
     * @param parent 父部门。
     * @param nodes  部门信息。
     */
    private void createSysDeptTree(final SysDept parent, final List<SysDept> nodes) {
        nodes.stream()
                .filter(node -> node.getDeptPid().equals(parent.getId()))
                .forEach(node -> {
                    parent.getChildren().add(node);
                    createSysDeptTree(node, nodes);
                });
    }

}
