package pos.presentation.estadisticas;
//==================================================================================================================
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
//==================================================================================================================
import java.util.Arrays;
import java.util.List;

class MatrixDataSet implements CategoryDataset {
    List<String> rowKeys;
    List<String> colKeys;
    float[][] data;

    public MatrixDataSet(String[] rows, String[] cols, float[][] data) {
        this.rowKeys = Arrays.asList(rows);
        this.colKeys = Arrays.asList(cols);
        this.data = data;
    }
//metodos para no hacer / si hacer la clase abstracta (generados por intellij)
    @Override
    public Comparable getRowKey(int row) {
        return rowKeys.get(row);
    }

    @Override
    public int getRowIndex(Comparable key) {
        return rowKeys.indexOf(key);
    }

    @Override
    public List getRowKeys() {
        return rowKeys;
    }

    @Override
    public Comparable getColumnKey(int col) {
        return colKeys.get(col);
    }

    @Override
    public int getColumnIndex(Comparable key) {
        return colKeys.indexOf(key);
    }

    @Override
    public List getColumnKeys() {
        return colKeys;
    }

    @Override
    public Number getValue(Comparable rowKey, Comparable colKey) {
        int rowIndex = getRowIndex(rowKey);
        int colIndex = getColumnIndex(colKey);
        return data[rowIndex][colIndex];
    }

    @Override
    public int getRowCount() {
        return rowKeys.size();
    }

    @Override
    public int getColumnCount() {
        return colKeys.size();
    }

    @Override
    public Number getValue(int row, int col) {
        return data[row][col];
    }

    @Override
    public void addChangeListener(DatasetChangeListener datasetChangeListener) {

    }

    @Override
    public void removeChangeListener(DatasetChangeListener datasetChangeListener) {

    }

    @Override
    public DatasetGroup getGroup() {
        return null;
    }

    @Override
    public void setGroup(DatasetGroup datasetGroup) {

    }
}
